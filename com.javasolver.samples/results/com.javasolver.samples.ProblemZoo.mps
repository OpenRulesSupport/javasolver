NAME          com.javasolver.samples.ProblemZoo
ROWS
 N  _OBJ_
 E  C1
 G  C2
 E  C3
COLUMNS
    M0000     'MARKER'                 'INTORG'
    Vi0                                C1                  30   C3                 400   
    Vi1                                C1                  40   C3                 500   
    Vi2                                C1                  -1   C2                   1   
    Vi3                                C3                  -1   
    M0001     'MARKER'                 'INTEND'
    Vi3                                _OBJ_                1   
RHS
    rhs                                C2                 300   
BOUNDS
 UP bnd                                Vi0                 30   
 UP bnd                                Vi1                 30   
 UP bnd                                Vi2               2100   
 UP bnd                                Vi3              27000   
ENDATA
