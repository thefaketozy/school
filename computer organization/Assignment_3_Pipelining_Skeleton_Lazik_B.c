/*
 Comp 222 - Fall 2019
 Seth Schroeder
 Programming Assignment 3: Pipelining
*/



#include <stdio.h>
#include <stdlib.h>

int num_ins;
struct instruction //Define structure for instruction containing fields for destination register, 2 source registers, and individual instruction delay
{
  int dest_reg, src_reg1, src_reg2, delay;
};
struct instruction* inst_array;

//struct Instruction* inst_array; //pointer to structure for creating a dynamic array of instructions


/*************************************************************/
void print_instructions() //FUNCTION TO PRINT OUT CHART OF INSTRUCTIONS WITH CYCLES WHEN EACH INSTRUCTION IS FETCHED 
{
  /* Declare local variables */
  int i;
  int j;
  int tab = 0;
  int cycle = 1;
  int total_cycles = (num_ins + 4);
  for (i = 1; i <= num_ins; i++)
  {
    total_cycles += inst_array[i].delay;
  }
  printf("Total number of cycles: %d\n", total_cycles);
  for (i = 1; i <= num_ins; i++)
  {
    cycle = cycle + inst_array[i].delay; //If there is a delay, increase cycles
    tab = (cycle - 1); //Calculate tabs needed
    printf("Instruction %d is fetched at cycle %d\n", i, cycle);
    printf("%d)\t", i);
    for(j = 0; j <= tab; j++) //Print tabs
    { 
      printf("\t");
    }
    printf("IF\tID\tEX\tMM\tWB\n"); //Print instruction
    cycle++;
  }
  return;
}


/*************************************************************/
void input_instructions() //FUNCTION TO ENTER INSTRUCTIONS
{
  /* Declare local variables, including an array of characters to store user input */
  int i;
  char user_input[8];
  printf("Enter number of instructions: "); //Prompt for total number of instructions
  scanf("%d", &num_ins);
  int elements = num_ins + 1;
  //Allocate memory to hold a set of instructions based on total number of instructions+1 (instruction 0 used for dependency checking)*/
  inst_array = malloc(elements * sizeof(*inst_array));
  inst_array[0].dest_reg = -1; // Initialize instruction 0's destination register to -1 to prevent false RAW dependency w/ instruction 2 */
  
  //For each instruction, prompt for user input with instruction number, such as: 1)   
  
  for (i = 1; i <= num_ins; i++)
  {
    printf("%d) ", i);
    scanf("%s", user_input);
    inst_array[i].dest_reg = (user_input[1] - '0');
    inst_array[i].src_reg1 = (user_input[4] - '0');
    inst_array[i].src_reg2 = (user_input[7] - '0');  
    printf("%s", user_input);  
  }
  
  return;
}


/*****************************************************/
void calc_instructions() //FUNCTION TO CALCULATE DELAY OF SET OF INSTRUCTIONS ON A 5-STAGE PIPLELINE ARCHITECTURE
{
    /* Declare local variables */
  int i;
    
  inst_array[1].delay = 0; // Set first isntruction delay to 0
  /* For each instruction i from 2 to total number of instructions, and */
  for (i = 2; i <= num_ins; i++)
  {
    inst_array[i].delay = 0; //initialize delay as 0
    /*check for dependency between instruction (i-2) and i, 
    as well as between instruction (i-1) and i */
    
    if (inst_array[i].src_reg1 == inst_array[i-1].dest_reg || inst_array[i].src_reg2 == inst_array[i-1].dest_reg)
    {
      inst_array[i].delay = 2;
    }
    
          
  else if (inst_array[i].src_reg1 == inst_array[i-2].dest_reg || inst_array[i].src_reg2 == inst_array[i-2].dest_reg)
  {
    {
      if (inst_array[i - 1].delay > 0) //checks for overlaps
      {
        inst_array[i].delay = 0; 
      }
      else inst_array[i].delay = 1;
    }

    
  }
}

  /* Calculate individual delay for current instruction */

  print_instructions(); //print chart
  return;
}


int main()
{
/* Until user quits, print menu, prompt for user input, and select corresponding function */

  int choice;
  int menu = 1;
    do{
      printf("Pipelined instruction performance\n\n");
      printf("1) Enter instructions\n2) Determine when instructions are fetched\n3) Exit\n\n");
      printf("Enter selection: ");
      scanf("%d", &choice);
      switch(choice) {
        case 1:
          input_instructions();
          continue;
        case 2:
          calc_instructions();
          continue;
        case 3:
          menu = 0;
          printf("Program Terminated Normally");
          break;
        default:
          continue;    
      }
    }while(menu == 1);

  

  return 0;
}
