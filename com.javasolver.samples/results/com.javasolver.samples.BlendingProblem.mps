NAME          com.javasolver.samples.BlendingProblem
ROWS
 N  _OBJ_
 E  C1
 G  C2
 E  C3
 E  C4
 E  C5
 E  C6
 L  C7
 L  C8
 L  C9
 G  C10
 L  C11
 G  C12
 L  C13
 G  C14
 L  C15
 L  C16
COLUMNS
    M0000     'MARKER'                 'INTORG'
    Vi0                                C1                  30   
    Vi1                                C1                  40   
    Vi2                                C1                  -1   C2                   1   
    Vi3                                C3                  21   C4                   1   
    Vi3                                C7                   1   C10                  2   
    Vi3                                C11                 -1   C16                  1   
    Vi4                                C3                  11   C5                   1   
    Vi4                                C7                   1   C12                  4   
    Vi4                                C13                 -2   C16                  1   
    Vi5                                C3                   1   C6                   1   
    Vi5                                C7                   1   C14                  6   
    Vi5                                C15                 -1   C16                  1   
    Vi6                                C3                  31   C4                   1   
    Vi6                                C8                   1   C10                 -4   
    Vi6                                C11                  1   C16                  1   
    Vi7                                C3                  21   C5                   1   
    Vi7                                C8                   1   C12                 -2   
    Vi7                                C13                  0   C16                  1   
    Vi8                                C3                  11   C6                   1   
    Vi8                                C8                   1   C14                  0   
    Vi8                                C15                  1   C16                  1   
    Vi9                                C3                  41   C4                   1   
    Vi9                                C9                   1   C10                 -2   
    Vi9                                C11                  2   C16                  1   
    Vi10                               C3                  31   C5                   1   
    Vi10                               C9                   1   C12                  0   
    Vi10                               C13                  1   C16                  1   
    Vi11                               C3                  21   C6                   1   
    Vi11                               C9                   1   C14                  2   
    Vi11                               C15                  2   C16                  1   
    Vi12                               C3                  -1   C4                 -10   
    Vi13                               C3                  -1   C5                 -10   
    Vi14                               C3                  -1   C6                 -10   
    Vi15                               C3                  -1   
    M0001     'MARKER'                 'INTEND'
    Vi15                               _OBJ_               -1   
RHS
    rhs                                C2                 300   C4                3000   
    rhs                                C5                2000   C6                1000   
    rhs                                C7                5000   C8                5000   
    rhs                                C9                5000   C16              14000   
BOUNDS
 UP bnd                                Vi0                 30   
 UP bnd                                Vi1                 30   
 UP bnd                                Vi2               2100   
 UP bnd                                Vi3              14000   
 UP bnd                                Vi4              14000   
 UP bnd                                Vi5              14000   
 UP bnd                                Vi6              14000   
 UP bnd                                Vi7              14000   
 UP bnd                                Vi8              14000   
 UP bnd                                Vi9              14000   
 UP bnd                                Vi10             14000   
 UP bnd                                Vi11             14000   
 UP bnd                                Vi12             14000   
 UP bnd                                Vi13             14000   
 UP bnd                                Vi14             14000   
 UP bnd                                Vi15           6888000   
ENDATA
