::Coded by Davoleo::
mkdir build

:: Compile the averager source file
g++ ./theory/media.cpp -o ./build/media

:: Compile the statement theory file
g++ ./theory/statements.cpp -o ./build/statements

::Compile date validator
g++ ./exercises/date_validator.cpp -o ./build/date_validator