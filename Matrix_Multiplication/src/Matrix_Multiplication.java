//      Name:           Auksorn, Auraiporn
//      Project:        1
//      Due:            Tuesday October 29, 2020
//      Course:         cs-3110-f20
//      Description:	The program perform a matrix multiplication of the classical algorithm, the divide-and-conquer algorithm, and the Strassen's algorithm


import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Matrix_Multiplication {

		/**
		 * Create a matrix by generating random elements of integer type in a of 10 positive numbers and return a matrix. 
		 * @param  size   the size of matrix 
		 * @return        the generated matrix
		*/
		public static int[][] generateMatrix(int size) {
			Random elements = new Random();
			int matrix[][] = new int[size][size];
			for(int i=0; i<size; i++) {
				for(int j=0; j<size ;j++) {
					matrix[i][j] = elements.nextInt(10);
				}
			}
			return matrix;
		}
		
		/**
		 * Multiply two matrices by using three for-loop. 
		 * @param  matrix1   the 2 dimensional array of a matrix 
		 * @param  matrix2   the 2 dimensional array of a matrix 
		 * @return        	 the result of matrix multiplication
		*/		
		public static int[][] classical_matrix_multiplication(int [][]matrix1, int [][] matrix2) {
			int size = matrix1.length;
			int result[][] = new int [size][size];
			for(int i =0;i<size;i++) {
				for(int j=0;j<size;j++) {
					result[i][j]=0;
					for(int k = 0; k<size; k++) {
						result[i][j]+= matrix1[i][k]*matrix2[k][j];
					}
				}
			}
			return result;				
		}
		
		/**
		 * Multiply two matrices by using divide-and-conquer algorithm. 
		 * @param  matrix1   the 2 dimensional array of a matrix 
		 * @param  matrix2   the 2 dimensional array of a matrix 
		 * @return        	 the result of matrix multiplication
		*/
		public static int[][] divide_conquer(int[][] matrix1, int[][] matrix2){
			int size = matrix1.length;
			int result[][] = new int[size][size];
			if(size == 1) {
				result[0][0] = matrix1[0][0]* matrix2[0][0];
			}
			if(size >= 2) {
				int divide_size = size/2;
				int[][] matrix1_11 = new int[divide_size][divide_size];
				int[][] matrix1_12 = new int[divide_size][divide_size];
				int[][] matrix1_21 = new int[divide_size][divide_size];
				int[][] matrix1_22 = new int[divide_size][divide_size];
				
				int[][] matrix2_11 = new int[divide_size][divide_size];
				int[][] matrix2_12 = new int[divide_size][divide_size];
				int[][] matrix2_21 = new int[divide_size][divide_size];
				int[][] matrix2_22 = new int[divide_size][divide_size];
				
				// Split two matrices into sub-matrices 
				splitMatrix(matrix1, matrix1_11, 0, 0);
				splitMatrix(matrix1, matrix1_12, 0, divide_size);
				splitMatrix(matrix1, matrix1_21, divide_size, 0);
				splitMatrix(matrix1, matrix1_22, divide_size, divide_size);
				
				splitMatrix(matrix2, matrix2_11, 0, 0);
				splitMatrix(matrix2, matrix2_12, 0, divide_size);
				splitMatrix(matrix2, matrix2_21, divide_size, 0);
				splitMatrix(matrix2, matrix2_22, divide_size, divide_size);
				
				int[][] x1,x2,x3,x4,x5,x6,x7,x8;
				// x1 = Matrix1 11 * Matrix2 11
				x1 = divide_conquer(matrix1_11, matrix2_11);
				// x2 = Matrix1 12 * Matrix2 21
				x2 = divide_conquer(matrix1_12, matrix2_21);
				// x3 = Matrix1 11 * Matrix2 12
				x3 = divide_conquer(matrix1_11, matrix2_12);
				// x4 = Matrix1 12 * Matrix2 22			
				x4 = divide_conquer(matrix1_12, matrix2_22);
				// x5 = Matrix1 21 * Matrix2 11
				x5 = divide_conquer(matrix1_21, matrix2_11);
				// x6 = Matrix1 22 * Matrix2 21
				x6 = divide_conquer(matrix1_22, matrix2_21);
				// x7 = Matrix1 21 * Matrix2 12
				x7 = divide_conquer(matrix1_21, matrix2_12);
				// x8 = Matrix1 22 * Matrix2 22
				x8 = divide_conquer(matrix1_22, matrix2_22);
				
				int[][] c11 = add(x1,x2);
				int[][] c12 = add(x3,x4);
				int[][] c21 = add(x5,x6);
				int[][] c22 = add(x7,x8);
				
				// Put all sub-matrices into one matrix
				combineMatrix(c11, result, 0 , 0);
				combineMatrix(c12, result, 0 , divide_size);
				combineMatrix(c21, result, divide_size , 0 );
				combineMatrix(c22, result, divide_size , divide_size);
				
			}
			return result;
		}
				
		/**
		 * Multiply two matrices by using Strassen's algorithm
		 * @param  matrix1   the 2 dimensional array of a matrix 
		 * @param  matrix2   the 2 dimensional array of a matrix 
		 * @return        	 the result of matrix multiplication
		*/
		public static int[][] strassen(int[][] matrix1, int[][] matrix2){
			int size = matrix1.length;
			int result[][] = new int[size][size];
			if(size == 1) {
				result[0][0] = matrix1[0][0]*matrix2[0][0];
			}
			if(size >= 2) {
				// Create sub-matrices 
				int divide_size = size/2;
				int[][] matrix1_11 = new int[divide_size][divide_size];
				int[][] matrix1_12 = new int[divide_size][divide_size];
				int[][] matrix1_21 = new int[divide_size][divide_size];
				int[][] matrix1_22 = new int[divide_size][divide_size];
				
				int[][] matrix2_11 = new int[divide_size][divide_size];
				int[][] matrix2_12 = new int[divide_size][divide_size];
				int[][] matrix2_21 = new int[divide_size][divide_size];
				int[][] matrix2_22 = new int[divide_size][divide_size];
				
				// Split two matrices into sub-matrices 
				splitMatrix(matrix1, matrix1_11, 0, 0);
				splitMatrix(matrix1, matrix1_12, 0, divide_size);
				splitMatrix(matrix1, matrix1_21, divide_size, 0);
				splitMatrix(matrix1, matrix1_22, divide_size, divide_size);
				
				splitMatrix(matrix2, matrix2_11, 0, 0);
				splitMatrix(matrix2, matrix2_12, 0, divide_size);
				splitMatrix(matrix2, matrix2_21, divide_size, 0);
				splitMatrix(matrix2, matrix2_22, divide_size, divide_size);
				
				int[][] p1,p2,p3,p4,p5,p6,p7;
				p1 = strassen(matrix1_11, subtract(matrix2_12, matrix2_22));
				p2 = strassen(add(matrix1_11, matrix1_12), matrix2_22);
				p3 = strassen(add(matrix1_21, matrix1_22), matrix2_11);
				
				p4 = strassen(matrix1_22, subtract(matrix2_21, matrix2_11));
				
				p5 = strassen(add(matrix1_11, matrix1_22), add(matrix2_11, matrix2_22));
				
				p6 = strassen(subtract(matrix1_12, matrix1_22), add(matrix2_21, matrix2_22));
				
				p7 = strassen(subtract(matrix1_11, matrix1_21), add(matrix2_11, matrix2_12));
				
				// C11 = P5 + P4 - P2 + P6
				// add ( subtract ( add(p5,p4) , p2 ) , p6)
				int[][] c11 = add(subtract(add(p5,p4),p2), p6);
				// C12 = P1 + P2
				int[][] c12 = add(p1,p2);
				// C21 = P3 + P4
				int[][] c21 = add(p3,p4);
				// C22 = P1 + P5 - P3 - P7
				int[][] c22 = subtract(subtract(add(p1,p5), p3), p7);
								
				// Put all sub-matrices into one matrix
				combineMatrix(c11, result, 0 , 0);
				combineMatrix(c12, result, 0 , divide_size);
				combineMatrix(c21, result, divide_size , 0 );
				combineMatrix(c22, result, divide_size , divide_size);
			}
			return result; 
		}
		
		/**
		 * Split a matrix into sub-matrices
		 * @param  matrix    		the 2 dimensional array of a matrix
		 * @param  matrix2   		the 2 dimensional array of a sub-matrix
		 * @param  rowMatrix 		row a matrix
		 * @param  columnMatrix   	column of a matrix
		*/				
		public static void splitMatrix(int[][] matrix,  int[][] child_matrix, int rowMatrix, int columnMatrix) {
			for(int i = 0, i1 = rowMatrix; i < child_matrix.length; i++, i1++) {
				for(int j = 0, j1 = columnMatrix; j < child_matrix.length; j++, j1++) {
					child_matrix[i][j] = matrix[i1][j1];
				}
			}
			
		}
		
		/**
		 * Perform matrix addition
		 * @param  matrix1   the 2 dimensional array of a matrix 
		 * @param  matrix2   the 2 dimensional array of a matrix 
		 * @return        	 the result of matrix addition
		*/
		public static int[][] add(int[][]matrix1, int[][]matrix2) {
			int length = matrix1.length;
			int[][] result = new int[length][length];
			for(int i = 0; i < length; i++) {
				for(int j = 0; j < length; j++) {
					result[i][j] = matrix1[i][j] + matrix2[i][j];
				}
			}
			return result;
		}
		
		/**
		 * Perform matrix subtraction
		 * @param  matrix1   the 2 dimensional array of a matrix 
		 * @param  matrix2   the 2 dimensional array of a matrix 
		 * @return        	 the result of matrix subtraction
		*/
		public static int[][] subtract(int[][]matrix1, int[][]matrix2) {
			int length = matrix1.length;
			int[][] result = new int[length][length];
			for(int i = 0; i < length; i++) {
				for(int j = 0; j < length; j++) {
					result[i][j] = matrix1[i][j] - matrix2[i][j];
				}
			}
			return result;
		}
		
		/**
		 * Combine sub-matrices into a matrix of size n
		 * @param  matrix    		the 2 dimensional array of a matrix
		 * @param  matrix2   		the 2 dimensional array of a sub-matrix
		 * @param  rowMatrix 		row a matrix
		 * @param  columnMatrix   	column of a matrix
		*/
		public static void combineMatrix(int[][] child_matrix,  int[][] matrix, int rowMatrix, int columnMatrix) {
			for(int i = 0, i1 = rowMatrix; i < child_matrix.length; i++, i1++) {
				for(int j = 0, j1 = columnMatrix; j < child_matrix.length; j++, j1++) {
					matrix[i1][j1] = child_matrix[i][j];
				}
			}
			
		}
		
		/**
		 * Display the result of a matrix multiplication 
		 * @param  result	an array of a result of matrix multiplication
		*/
		public static void displayResult(int[][] result) {
			System.out.println("Product of two matrices is: ");
		    for(int[] row : result) {
		    	for (int column : row) {
		    		System.out.print(column + "    ");
		        }
		        System.out.println();
		    }
		}
		
		/**
		 * Calculate the average of time taken of each matrix by obtaining the sum of all trials, then divided by number of times
		 * (average = (trial #1 + trial #2 + trial #3 + ... + trial #n) / NUMBER_OF_TIMES to run each matrix multiplication)  
		 * @param  array 	an array of time taken of matrix multiplication
		*/
		public static long average(long [] array) {
			long sum = 0;
			for(int i = 0; i< array.length; i++) {
				sum += array[i];
			}
			return sum / (long) array.length;
		}
		
		/**
		 * Calculate the total time to perform the task
		 * (total time = (Average time of matrix #1 + Average time of matrix #2 + ... + Average time of matrix #n)/ total number of input matrices (MAXIMUM_DATA_SETS))
		 * @param  average	an array of average time of each matrix 
		 * @param  totalNumberDataSets	total number of matrices 
		*/
		public static long calculateTotalTime(long[] average, int totalNumberDataSets) {
			long total_time = 0;
			for(int i = 0; i < average.length; i++) {
				total_time += average[i];
			}
			return total_time / (long) totalNumberDataSets ;
		}
		
		
		public static void main(String [] args) {
			/**
			 The algorithm is tested with the same matrices of different sizes many times.
			 The total time spent is then divided by 
			 the number of times the algorithm is performed
			 to obtain the time taken to solve the give instance.
			 
			  In this program, we generate matrices by defining the maximum size of matrices we will test.
			  i.e., the range of matrix size would be 2-256, which is increasing by (2^n)
			  		number of times = 20 times 
			  		total time spent = time taken of each algorithm/ number of times 
			 */
			
			final int NUMBER_OF_TIMES = 20, MAXIMUM_DATA_SETS = 10, SIZE = 2;
			
			int[][] matrix1, matrix2;
			long [] time_taken_C = new long [NUMBER_OF_TIMES];
			long [] time_taken_D = new long [NUMBER_OF_TIMES] ;
			long [] time_taken_S = new long [NUMBER_OF_TIMES];
			long [] aveC = new long [NUMBER_OF_TIMES];
			long [] aveD = new long [NUMBER_OF_TIMES];
			long [] aveS = new long [NUMBER_OF_TIMES];
			long start, end, totalTimeC = 0,  totalTimeD = 0,  totalTimeS = 0;
			
			System.out.println("Size: " + SIZE + "*" + SIZE);
			// Generate 1000 sets of matrix that have the same n*n size 
			for (int i = 0; i < MAXIMUM_DATA_SETS; i++) {
				matrix1 = generateMatrix(SIZE);
				matrix2 = generateMatrix(SIZE);				
				
				System.out.println("Time taken of a data set #" + (i+1) + " out of total data sets of " + MAXIMUM_DATA_SETS + "\n");
				
				// for each set of matrices, we multiply it by 3 algorithms for 20 times 
				for (int j = 0; j < NUMBER_OF_TIMES; j++) {
					
					start = System.nanoTime();
					classical_matrix_multiplication(matrix1, matrix2);
					end = System.nanoTime();
					
					time_taken_C[j] =  end - start;
					System.out.println("Time taken of Classical at trial #"+ (j+1) + "is: " + time_taken_C[j] );	
					
					start = System.nanoTime();
					divide_conquer(matrix1, matrix2);
					end = System.nanoTime();
					
					time_taken_D[j] = end - start;
					System.out.println("Time taken of Divide-and-conquer at trial #"+ (j+1) + "is: " + time_taken_D[j] );	
										
					start = System.nanoTime();
					strassen(matrix1, matrix2);
					end = System.nanoTime();
					
					time_taken_S[j] = end - start;
					System.out.println("Time taken of Strassen's at trial #"+ (j+1) + "is: " + time_taken_S[j] );	
				}
				aveC[i] = average(time_taken_C);				
				aveD[i] = average(time_taken_D);
				aveS[i] = average(time_taken_S);
							
				System.out.println("Average time of Classical Matrix Multiplication of a matrix #" + (i+1) + " out of total data sets of " + MAXIMUM_DATA_SETS + " is: " + aveC[i] +"\n");
				System.out.println("Average time of Divide-and-conquer Matrix Multiplication of a matrix #" + (i+1) + " out of total data sets of " + MAXIMUM_DATA_SETS + " is: " + aveD[i] +"\n");
				System.out.println("Average time of Strassen's Multiplication of a matrix #" + (i+1) + " out of total data sets of " + MAXIMUM_DATA_SETS + " is: " + aveS[i] +"\n");
			}		
			totalTimeC = calculateTotalTime(aveC, MAXIMUM_DATA_SETS);
			totalTimeD = calculateTotalTime(aveD, MAXIMUM_DATA_SETS);
			totalTimeS = calculateTotalTime(aveS, MAXIMUM_DATA_SETS);
			System.out.println("----------------------------The total time of each algorithm in nanosecond-----------------------------");
			System.out.println("Total time of Classical Matrix Multiplication is: "+ totalTimeC + " nanoseconds");
			System.out.println("Total time of Divide-and-conquer Matrix Multiplication is: "+ totalTimeD + " nanoseconds");
			System.out.println("Total time of Strassen's Multiplication is: "+ totalTimeS + " nanoseconds\n");
			
			double total_C_In_Millisecond = (double) totalTimeC / 1_000_000;
			double total_D_In_Millisecond = (double) totalTimeD / 1_000_000;
			double total_S_In_Millisecond = (double) totalTimeS / 1_000_000;
			// TimeUnit
            long convert_C = TimeUnit.MILLISECONDS.convert(totalTimeC, TimeUnit.NANOSECONDS);      
            long convert_D = TimeUnit.MILLISECONDS.convert(totalTimeD, TimeUnit.NANOSECONDS);         
            long convert_S = TimeUnit.MILLISECONDS.convert(totalTimeS, TimeUnit.NANOSECONDS);    
            
			System.out.println("----------------------------The total time of each algorithm in millisecond-----------------------------");
			System.out.println("Total time of Classical Matrix Multiplication is: "+  total_C_In_Millisecond + " ≈ " + convert_C + " milliseconds");
			System.out.println("Total time of Divide-and-conquer Matrix Multiplication is: "+ total_D_In_Millisecond + " ≈ " +convert_D + " milliseconds");
			System.out.println("Total time of Strassen's Multiplication is: "+ total_S_In_Millisecond + " ≈ " + convert_S + " milliseconds\n");
			
			/** The code comment below was how I misunderstood details regarding the total time. 
			* The total time spent is divided by the number of times the algorithm is performed to obtain the time taken to solve the given instance 
			long total_C = time_taken_C / (long) NUMBER_OF_TIMES;
			long total_D  = time_taken_D/ (long) NUMBER_OF_TIMES;
			long total_S = time_taken_S / (long) NUMBER_OF_TIMES;
			System.out.println("Time taken to multiply one set of data (two matrices) with classical by " +  NUMBER_OF_TIMES + " times is " 
					+ time_taken_C + " nanoseconds.\n" +
					
					"Time taken to multiply one set of data (two matrices) with divide&conquer by" +  NUMBER_OF_TIMES + " times is " 
					+ time_taken_D + " nanoseconds.\n" +
					
					"Time taken to multiply one set of data (two matrices) wite strassen by "       +  NUMBER_OF_TIMES + " times is " 
					+ time_taken_S + " nanoseconds." );

			System.out.println();
			System.out.println("The total time spent of classical is \t" + total_C + " nanoseconds OR \t" + total_C_In_Millisecond +  " ≈ " + convert_C + " miliseconds.\n" +
					"The total time spent of divide-and-conquer is \t" + total_D + " nanoseconds OR \t" + total_D_In_Millisecond + " ≈ " + convert_D + " miliseconds.\n" +
					"The total time spent of Strassen 's is \t" + total_S + " nanoseconds OR \t" + total_S_In_Millisecond +  " ≈ " + convert_S + " miliseconds.\n");
			*/						
		}
}
