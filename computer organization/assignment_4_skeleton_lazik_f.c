/*
Seth Schroeder
Programming Assignment 4: Cache Simulation
Comp 222 – Fall 2019
*/

//Declare Variables
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Define Structures
typedef struct node{
  int tag;
  int *block;
  
  //*cache = NULL;
} node;
struct node *cache;

typedef struct node n;

// global vars
int mem_size;
int cache_size;
int block_size;
int valid_data = 0;
int *mm;

//Procedure to Print Prologue
//
void writePrologue() 
{
  printf("Programming Assignment 4: Cache Simulation\n");
  printf("Comp 222 - Fall 2019\n");
}

//Procedure to Print Menu
//
void printMenuHeader() {
  printf("\nMain Menu - Main Memory to Cache Memory Mapping\n");
  printf("------------------------------------------------\n");
  printf("1) Enter Configuration Parameters\n");
  printf("2) Read from Cache\n");
  printf("3) Write to Cache\n");
  printf("4) Quit Program\n\n");
}

//Procedure to Check for Power of 2

int power (int i) {
  while (i % 2 == 0) {
    i /= 2;
  }
  if (i == 1) {
    return 1;
  }
  else {
    return 0;
  }
}


// Procedure to input parameters

void enter_params() {
  
  printf("Enter main memory size (words): ");
  scanf("%d", &mem_size);
  
  printf("Enter cache size (words): ");
  scanf("%d", &cache_size);
  
  printf("Enter block size (words/block): ");
  scanf("%d", &block_size);

  if (power(mem_size) == 0){ // checks for powers of 2
    printf("*** Error - Main Memory Size is not a Power of 2\n");
    return;
  }
  else if (power(cache_size) == 0) {
    printf("*** Error - Cache Size is not a Power of 2\n");
    return;
  }
  else if (power(block_size) == 0) {
    printf("*** Error - Block Size is not a Power of 2\n");
    return;
  }
  else if (block_size > cache_size) {
    printf("*** Error - Block size is larger than cache size\n");
    return;
  }
  printf("*** All Input Parameters Accepted. Starting to Process Write/Read Requests\n");
  //call something here
  valid_data = 1;
  int i;
  mm = (int*)malloc(mem_size * sizeof(int));
  for(i = 0; i < mem_size; i++){
    mm[i] = mem_size - i;
  }
  cache = (n*)malloc(cache_size * sizeof(n));
  for(i = 0; i < cache_size; i++){
    cache[i].block = NULL;
  }

  return;

}

void read_cache() {
  int addr;
  int cache_addr;
  int tag;
  int word = 0; //use this later
  
  printf("Enter Main Memory Address to Read: ");
  scanf("%d", &addr);
  if (addr > mem_size) {
    printf("*** Error – Read Address Exceeds Memory Address Space\n");
    return;
  }
  word = (addr % block_size);
  cache_addr = (addr % (cache_size / block_size)) + ((block_size - 1) - word);
  tag = addr / cache_size;
  if (cache[cache_addr].tag != tag) { //What to do if there is a miss
    printf("*** Read Miss - First Load Block from Memory\n");
    cache[cache_addr].tag = tag;
    cache[cache_addr].block = &mm[addr];
    int value = *cache[cache_addr].block;
    printf("*** Word %d of Cache Line %d with Tag %d contains the Value %d", word, cache_addr, cache[cache_addr].tag, value);
  } 
  else { // What to do if there's a hit
  int value = *cache[cache_addr].block;
  printf("*** Cache Hit");
  printf("*** Word %d of Cache Line %d with Tag %d contains the Value %d", word, cache_addr, cache[cache_addr].tag, value);
  }

  return;
  
}

/* Write Cache */
void write_cache() {
  int addr;
  int cache_addr;
  int tag;
  int word; //use this later
  int value;
  
  printf("Enter Main Memory Address to Write: ");
  scanf("%d", &addr);
  printf("Enter value to write: ");
  scanf("%d", &value);
  if (addr > mem_size) {
    printf("*** Error – Write Address Exceeds Memory Address Space\n");
    return;
  }
  word = (addr % block_size);
  cache_addr = (addr % (cache_size / block_size)) + ((block_size - 1) - word);
  tag = addr / cache_size;
  if (cache[cache_addr].tag != tag) { //What to do if there is a miss
    printf("*** Write Miss - First load block from memory\n");
    cache[cache_addr].tag = tag;
    mm[addr] = value;
    cache[cache_addr].block = &mm[addr];
    printf("*** Word %d of Cache Line %d with Tag %d contains the Value %d", word, cache_addr, cache[cache_addr].tag, value);
  } 
  else { // What to do if there's a hit
    mm[addr] = value;
    cache[cache_addr].block = &mm[addr];
    printf("*** Cache Hit");
    printf("*** Word %d of Cache Line %d with Tag %d contains the Value %d", word, cache_addr, cache[cache_addr].tag, value);
  }

  return;
}
  

/* Main Program */

int main () {

  writePrologue();

 //Print menu
  int choice;
  int menu = 1;
    do{
      printMenuHeader();
      printf("Enter selection: ");
      scanf("%d", &choice);
      fflush(stdin);
      switch(choice) {
        case 1:
          enter_params();
          continue;
        case 2:
          //check for input entered or error
          if (valid_data) {
            read_cache();
          }
          else {
            printf("*** Error – Invalid Menu Option Selected\n");
          }           
          continue;
        case 3:
          if (valid_data) {
            write_cache();
          }
          else {
            printf("*** Error - Invalid Menu Option Selected\n");
          }
          continue;
        case 4:
          menu = 0;
          printf("*** Memory Freed Up - Program Terminated Normally");
          free(cache);
          free(mm);
          break;
        default:
          continue;    
      }
    }while(menu == 1);

  return 0;
}



