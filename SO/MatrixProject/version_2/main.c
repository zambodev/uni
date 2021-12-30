#include <stdio.h>
#include <stdlib.h>

#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>

#define MATRIX_SIZE 32

void error(const char* message) {
	printf("%s\n", message);
	exit(-1);
}

/**
 * TODO Document
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
 * TODO Document
 */
void load_matrix(long matrix[MATRIX_SIZE][MATRIX_SIZE], int* x, int* y, const char* filename) {
	int matrix_fd = open(filename, O_RDONLY);
	
	char char_buff;
	*x = 0;
	
	*y = 0;

	while(read(matrix_fd, &char_buff, 1) != 0) {
		if(char_buff >= '0' && char_buff <= '9') {
			matrix[*x][*y] = char_buff - 48;
			(*x)++;
		}
		else if(char_buff == '\n')
		{
			(*y)++;
			(*x) = 0;
		}
	}
	(*y)++;

	close(matrix_fd);
}

/**
 * \param pipe The pipe through which column values are sent and received
 * \param row The row of the first matrix this process is working on
 * \param row_i The index of the first element of the row the process is working on
 * \param result_row The row of the resulting matrix
 */
void compute_row(int pipe[2], long row[MATRIX_SIZE], int row_i, long result_row[MATRIX_SIZE]) {

	long col_vals[3];
		for(int i=0; i < MATRIX_SIZE; i++)
	{
		puts("yo boi eccomi");

		while(read(pipe[0], col_vals, 3 * sizeof(long)) == 0) {
			//wait if the pipe is empty
		}
	
		printf("column values: %ld %ld %ld\n", col_vals[0], col_vals[1], col_vals[2]);

	}

}

int main() {
	
	long matrix1[MATRIX_SIZE][MATRIX_SIZE];
	long matrix2[MATRIX_SIZE][MATRIX_SIZE];
	long result[MATRIX_SIZE][MATRIX_SIZE];

	int matrix1_x = 0;
	int matrix1_y = 0;
	int matrix2_x = 0;
	int matrix2_y = 0;


	load_matrix(matrix1, &matrix1_x, &matrix1_y, "../matrice1.txt");
	load_matrix(matrix2, &matrix2_x, &matrix2_y, "../matrice2.txt");

	int pipe1[2];
	int pipe2[2];
	int pipe3[2];
	

	int pid1 = fork();
	if (pid1 == -1) {
		error("ERROR: while forking the first process");
	}
	else if(pid1 == 0) {	
		//1st Child Process
		//Pipe creation
		pipe(pipe1);
		//Pipe linking
		//writing end of pipe 2 to reading end of pipe 1
		dup2(pipe2[1], pipe1[0]);
		//writing end of pipe 1 to reading end of pipe 3
		dup2(pipe1[1], pipe3[0]);

		compute_row(pipe1, matrix1[0], 0, result[0]);
	}
	else {	// THE PARENT
		
		// ----- creating the 2nd process -----
		int pid2 = fork();
		if (pid2 == -1) {
			error("ERROR: while forking the first process");
		}
		if(pid2 == 0) {
			// child 2
			//Pipe creation
			pipe(pipe2);
			//Pipe linking
			//writing end of pipe 3 to reading end of pipe 2
			dup2(pipe3[1], pipe2[0]);
			//writing end of pipe 2 to reading end of pipe 1
			dup2(pipe2[1], pipe1[0]);
			
			long arr[] = {1, 2, 3};
			write(pipe2[1], arr, 3 * sizeof(long));
		}
		else { // THE PARENT
			
			int pid3 = fork();
			if (pid3 == -1) {
				error("ERROR: while forking the first process");
			}
			if(pid3 == 0) {
				//child 3
				//Pipe creation
				pipe(pipe2);
				//Pipe linking
				//writing end of pipe 1 to reading end of pipe 3
				dup2(pipe1[1], pipe3[0]);
				//writing end of pipe 3 to reading end of pipe 2
				dup2(pipe3[1], pipe2[0]);
			}
			else {
				
			}
		}
		// ----- 2nd process if finished -----
	}

	puts("End of padre");
}


