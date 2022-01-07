#ifndef UTILS_H
#define UTILS_H

#include <stdlib.h>
#include <stdio.h>

#include <fcntl.h>
#include <sys/types.h>
#include <unistd.h>
#include <math.h>

//Benchmarking
#include <time.h>

//Maximum Matrix Size constant (can be modified) [used to initialize arrays at the beginning of programs execution]
#define MATRIX_SIZE 200

/**
 * \brief Prints a matrix to the standard output (debug purposes)
 * \param matrix the matrix to print
 * \param x_limit the horizontal length of the matrix
 * \param y_limit the vertical length of the matrix
 */
void print_matrix(long matrix[MATRIX_SIZE][MATRIX_SIZE], int x_limit, int y_limit) {
	for(int y=0; y < y_limit; y++)
	{
		for(int x=0; x < x_limit; x++)
		{
			printf("%ld ", matrix[x][y]);
		}
		printf("\n");
	}
}

/**
 * \brief Loads a matrix from a text file (super inefficient as we're loading it char by char)
 * The Matrix in the text file is written in rows with each cell horizontally 
 * separated by a non-numerical character (not a '\n') from others in the same row
 * \param out_matrix the matrix that is loaded from the text file
 * \param x used as a column counter inside of this function (equals the actual horizontal matrix length at the end of the function)
 * \param y used as a row counter inside of this function (equals the actual vertical matrix length at the end of the function)
 * \param filename the name of the file containing the matrix
 */
void load_matrix(long out_matrix[MATRIX_SIZE][MATRIX_SIZE], int* x, int* y, const char* filename) {
	
	FILE* matrix_fd = fopen(filename, "r");
	*x = 0;
	*y = 0;

	while(fscanf(matrix_fd, "%ld", &out_matrix[*x][*y]) > 0) {
		(*x)++;
	}

	*y = sqrt(*x);
	*x = *y;

	//Close matrix file descriptor
	fclose(matrix_fd);
}

/**
 * \brief Prints an error message and exits execution with an "error" status code
 **/
void error(const char* message) {
	printf("ERROR! %s\n", message);
	exit(-1);
}

/**
 * \brief Macro with the code to benchmark as parameter
 **/
#define BENCHMARK(code) clock_t bench_begin = clock(); \
code; \
clock_t bench_end = clock(); \
printf("Execution time: %lf milliseconds\n", (double)(bench_end - bench_begin) * 1000 / CLOCKS_PER_SEC);

#endif