/*
 Comp 222 - Fall 2019
 Seth Schroeder
 Programming Assignment 1: Performance
*/

#include <stdio.h>
#include <stdlib.h>

//initialize values
//
int * cpi_i; //define cpi_i as a pointer to one of more integers
int * count_i;
int mhz;
int classes;

//
// procedure to read all input parameters
//
void enter_params()
{
  //initialize counter variables
  //
  int i;
  int cpi_sum=0;
  int instr_total=0;

  scanf("%d", &mhz);// input frequency
  
  scanf("%d",&classes);// input number of instruction classes

  cpi_i = (int *)malloc(classes*sizeof(int)); //dynamically allocate an array 
  count_i = (int *)malloc(classes*sizeof(int));//dynamically allocate a second array

  instr_total = 0;
  for (i=1; i <= classes; i++) 
    {
      scanf("%d", &cpi_i[i]);// input instruction's cpi
      scanf("%d", &count_i[i]);// input instruction's count
      instr_total += count_i[i];
      cpi_sum = cpi_sum + (cpi_i[i] * count_i[i]);
    }
  printf("\n");
  return;  
   
}

float calc_cycles() // Calculates total CPU cycles
{
  int i;
  float cycles = 0;
  for (i = 1; i <= classes; i++) {
    cycles += cpi_i[i] * count_i[i];
  }
  return cycles;
}

//function computes execution time
float calc_CPU_time()
{
  float time;
  time = (calc_cycles() / (float)mhz);
  return time;
}

int sum_instructions ()
{
  int total_instructions = 0;
  int i;
  for (i = 1; i <= classes; i++) { //Loop sums the total instructions executed
    total_instructions += count_i[i];
  }
  return total_instructions;
}

float calc_CPI(float time, int ins)
{
  float cpi;
  cpi = (time * (float)mhz) / (float)ins;
  return cpi;
}

//function computes mips
//
float calc_MIPS(float time, int ins)
{
  return (float)ins / time;
}

void print_params() //Outputs the input
{
  int i;
  printf("FREQUENCY (MHz): %d\n", mhz);
  printf("INSTRUCTION DISTRIBUTION\n");
  printf("CLASS\tCPI\tCOUNT\n");
  for (i = 1; i <= classes; i++) {
    printf("%d\t%d\t%d\n", i, cpi_i[i], count_i[i]);
  }
  printf("\n");
}

void print_performance() {
  //print the performance details
  printf("PERFORMANCE VALUES\n");
  float time = calc_CPU_time(); //We need time for CPI, only calculating it once
  int instructions = sum_instructions(); //Only do this once as well
  printf("AVERAGE CPI\t%.2f\n", calc_CPI(time, instructions));
  printf("TIME (ms)\t%.2f\n", (time * 1000)); //multiply time times 1000 to print results in ms
  printf("MIPS\t%.2f\n", calc_MIPS(time, instructions));
}

//main program keeps reading menu selection and dispatches accordingly
//
int main()
{
  //Print menu
  int choice;
  int menu = 1;
    do{
      scanf("%d", &choice);
      switch(choice) {
        case 1: //Enter params
          enter_params();
          continue;
        case 2: //Print Results
          print_params();
          print_performance();
          continue;
        case 3:
          menu = 0;
          printf("PROGRAM TERMINATED NORMALLY");
          break;
        default:
          continue;    
      }
    }while(menu == 1);
  free(cpi_i);//free up space previously allocated above
  free(count_i);
  return 0;
}


