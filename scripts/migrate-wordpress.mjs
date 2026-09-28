import fs from "node:fs";
import path from "node:path";

const root = path.resolve(import.meta.dirname, "..");
const sourceRoot = path.resolve(root, "..");
const docs = path.join(root, "docs");
const pages = JSON.parse(fs.readFileSync(path.join(sourceRoot, "pages.json"), "utf8"));
const posts = JSON.parse(fs.readFileSync(path.join(sourceRoot, "posts.json"), "utf8"));

const byId = new Map(pages.map((page) => [page.id, page]));
const siteOrigin = "https://javasolver.com";
const wordpressOrigin = "https://javasolvers.wordpress.com";
const homePage = pages.find((page) => page.id === 352);

const navigation = [
  ["Home", "/"],
  ["Download", "/download/"],
  ["Introductory Example", "/introductory-example/"],
  ["Define a Problem", "/defining-optimization-problem/"],
  ["Solve a Problem", "/solving-optimization-problem/"],
  ["Examples", "/learn-by-examples/"],
  ["Scheduling", "/scheduling-and-resource-allocation/"],
  ["Switching Solvers", "/switching-solvers/"],
  ["Business Rules + Optimization", "/brcp-lp/"],
  ["Microservices", "/decision-optimization-microservices/"],
  ["Support", "/support/"],
];

const childPages = pages
  .filter((page) => page.parent === 149)
  .sort((a, b) => a.menu_order - b.menu_order);

const media = new Map();

function stripTags(value) {
  return decodeEntities(value.replace(/<[^>]*>/g, " ")).replace(/\s+/g, " ").trim();
}

function decodeEntities(value) {
  return value
    .replace(/&nbsp;/g, " ")
    .replace(/&amp;/g, "&")
    .replace(/&quot;/g, '"')
    .replace(/&#039;|&apos;/g, "'")
    .replace(/&lt;/g, "<")
    .replace(/&gt;/g, ">");
}

function escapeHtml(value) {
  return value
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;");
}

function slugPath(page) {
  if (page.id === homePage.id) return "/";
  if (page.parent && byId.has(page.parent)) {
    return `/${byId.get(page.parent).slug}/${page.slug}/`;
  }
  return `/${page.slug}/`;
}

function localMediaUrl(rawUrl) {
  const decoded = decodeEntities(rawUrl);
  let parsed;
  try {
    parsed = new URL(decoded, wordpressOrigin);
  } catch {
    return rawUrl;
  }
  if (!/\.wordpress\.com$/i.test(parsed.hostname)) return rawUrl;
  if (!parsed.pathname.includes("/wp-content/") && !parsed.hostname.endsWith("files.wordpress.com")) return rawUrl;

  const original = `${parsed.origin}${parsed.pathname}`;
  const yearMonth = parsed.pathname.match(/\/(20\d{2})\/(\d{2})\//);
  const folder = yearMonth ? `${yearMonth[1]}-${yearMonth[2]}` : "misc";
  const filename = decodeURIComponent(path.posix.basename(parsed.pathname)).replace(/[^a-zA-Z0-9._-]/g, "-");
  const local = `/assets/media/${folder}/${filename}`;
  media.set(original, local);
  return local;
}

function cleanContent(html) {
  let content = html;
  content = content.replace(/<!--([\s\S]*?)-->/g, "");
  content = content.replace(/\s(?:data-[\w-]+|srcset|sizes)=("[^"]*"|'[^']*')/gi, "");
  content = content.replace(/\sloading=("[^"]*"|'[^']*')/gi, " loading=\"lazy\"");
  content = content.replace(/\sstyle=("[^"]*"|'[^']*')/gi, "");
  content = content.replace(/(<img\b[^>]*\bsrc=["'])([^"']+)(["'])/gi, (_, before, url, after) => `${before}${localMediaUrl(url)}${after}`);
  content = content.replace(/href=(['"])(https?:\/\/javasolvers\.wordpress\.com)?(\/[^'"]*)\1/gi, (_, quote, _origin, pathname) => {
    const clean = pathname.replace(/\?.*$/, "");
    return `href=${quote}${clean}${quote}`;
  });
  content = content.replace(/href=(['"])http:\/\/javasolver\.com\/?\1/gi, 'href="/"');
  content = content.replace(/<p>\s*<\/p>/gi, "");
  return content.trim();
}

function breadcrumbs(page) {
  if (page.id === homePage.id) return "";
  const parent = page.parent ? byId.get(page.parent) : null;
  const links = ['<a href="/">Home</a>'];
  if (parent) links.push(`<a href="${slugPath(parent)}">${parent.title.rendered}</a>`);
  links.push(`<span aria-current="page">${page.title.rendered}</span>`);
  return `<nav class="breadcrumbs" aria-label="Breadcrumb">${links.join('<span aria-hidden="true">/</span>')}</nav>`;
}

function sidebar(currentPath) {
  const items = navigation.map(([label, href]) => {
    const active = currentPath === href || (href !== "/" && currentPath.startsWith(href));
    const nested = href === "/learn-by-examples/"
      ? `<div class="subnav">${childPages.map((page) => `<a ${currentPath === slugPath(page) ? 'class="active" aria-current="page"' : ""} href="${slugPath(page)}">${page.title.rendered}</a>`).join("")}</div>`
      : "";
    return `<a ${active ? 'class="active" aria-current="page"' : ""} href="${href}">${label}</a>${nested}`;
  }).join("");
  return `<aside class="sidebar" id="site-menu">
    <a class="brand" href="/" aria-label="Java Solver home">
      <span class="brand-mark" aria-hidden="true">JS</span>
      <span><strong>Java Solver</strong><small>Decision optimization in Java</small></span>
    </a>
    <nav class="site-nav" aria-label="Main navigation">${items}</nav>
    <div class="sidebar-links"><a href="https://github.com/OpenRulesSupport/javasolver">GitHub</a><a href="https://mvnrepository.com/artifact/com.javasolver/javasolver">Maven Central</a></div>
  </aside>`;
}

function pageHtml(page, options = {}) {
  const currentPath = options.path ?? slugPath(page);
  const title = options.home ? "Java Solver — Decision Optimization in Java" : `${stripTags(page.title.rendered)} — Java Solver`;
  const description = stripTags(page.excerpt?.rendered || page.content.rendered).slice(0, 158);
  const heading = options.home ? "Modeling and Solving Optimization Problems in Java" : page.title.rendered;
  const eyebrow = options.home ? "Open source · Java · JSR 331" : "Java Solver documentation";
  const actions = options.home ? `<div class="hero-actions"><a class="button primary" href="/introductory-example/">Start with an example</a><a class="button secondary" href="/download/">Download Java Solver</a></div>` : "";
  const children = page.id === 149 ? `<section class="example-grid" aria-labelledby="example-list"><h2 id="example-list">Worked examples</h2><div>${childPages.map((child) => `<a href="${slugPath(child)}"><span>${child.title.rendered}</span><span aria-hidden="true">→</span></a>`).join("")}</div></section>` : "";
  const modified = new Date(page.modified).toLocaleDateString("en-US", { month: "long", day: "numeric", year: "numeric", timeZone: "UTC" });

  return `<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${escapeHtml(title)}</title>
  <meta name="description" content="${escapeHtml(description)}">
  <link rel="canonical" href="${siteOrigin}${currentPath}">
  <link rel="icon" href="/assets/favicon.svg" type="image/svg+xml">
  <link rel="stylesheet" href="/assets/site.css">
</head>
<body>
  <a class="skip-link" href="#content">Skip to content</a>
  <button class="menu-button" type="button" aria-expanded="false" aria-controls="site-menu"><span aria-hidden="true">☰</span><span>Menu</span></button>
  <div class="layout">
    ${sidebar(currentPath)}
    <main id="content" class="main">
      ${breadcrumbs(page)}
      <header class="page-header ${options.home ? "home-header" : ""}">
        <p class="eyebrow">${eyebrow}</p>
        <h1>${heading}</h1>
        ${options.home ? '<p class="lede">Define optimization models with a small Java API, then solve them with interchangeable constraint and linear solvers.</p>' : ""}
        ${actions}
      </header>
      <article class="prose">${cleanContent(page.content.rendered)}</article>
      ${children}
      <footer class="page-footer"><span>Last updated ${modified}</span><a href="https://github.com/OpenRulesSupport/javasolver">View Java Solver on GitHub</a></footer>
    </main>
  </div>
  <script src="/assets/site.js"></script>
</body>
</html>`;
}

function write(relativePath, contents) {
  const destination = path.join(docs, relativePath);
  fs.mkdirSync(path.dirname(destination), { recursive: true });
  fs.writeFileSync(destination, contents);
}

fs.rmSync(docs, { recursive: true, force: true });
fs.mkdirSync(docs, { recursive: true });

write("index.html", pageHtml(homePage, { home: true, path: "/" }));
for (const page of pages) {
  if (page.id === homePage.id) continue;
  const output = slugPath(page).replace(/^\//, "");
  write(path.join(output, "index.html"), pageHtml(page));
}

// Preserve a few legacy aliases that appeared in older navigation and search results.
for (const page of pages.filter((item) => [80, 444, 450, 454].includes(item.id))) {
  const output = slugPath(page).replace(/^\//, "");
  write(path.join(output, "index.html"), pageHtml(page));
}

const paths = ["/", ...pages.filter((page) => page.id !== homePage.id).map(slugPath)];
write("sitemap.xml", `<?xml version="1.0" encoding="UTF-8"?>\n<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">\n${paths.map((route) => `  <url><loc>${siteOrigin}${route}</loc></url>`).join("\n")}\n</urlset>\n`);
write("robots.txt", `User-agent: *\nAllow: /\nSitemap: ${siteOrigin}/sitemap.xml\n`);
write("CNAME", "javasolver.com\n");
write(".nojekyll", "");
write("404.html", `<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Page not found — Java Solver</title><link rel="icon" href="/assets/favicon.svg" type="image/svg+xml"><link rel="stylesheet" href="/assets/site.css"></head><body><main class="not-found"><span class="brand-mark">JS</span><p class="eyebrow">404</p><h1>Page not found</h1><p>The requested Java Solver page does not exist.</p><a class="button primary" href="/">Return home</a></main></body></html>`);

fs.mkdirSync(path.join(docs, "assets"), { recursive: true });
for (const filename of ["site.css", "site.js", "favicon.svg"]) {
  fs.copyFileSync(path.join(root, "docs-template", filename), path.join(docs, "assets", filename));
}

write("assets/media-manifest.json", JSON.stringify([...media.entries()].map(([url, local]) => ({ url, local })), null, 2));
console.log(`Generated ${pages.length} content pages and ${media.size} local media references in ${docs}`);
