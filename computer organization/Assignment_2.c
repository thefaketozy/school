#include <stdio.h>
#include <math.h>
#include <float.h>
/*************************/
void dec_ieee()
{
	/* declare local variables */
	float input;
	float dec_num;
	int exp = 0;
	int expbits[8];
	int i = 0;
	/* prompt for floating point decimal number */ 
	printf("Enter the decimal representation:");

	if(scanf("%f", &dec_num) != 1) { // Checks for NaN
		printf("*** Sign: - *** Special case: NaN\n");
		fflush(stdin);
		return;
	}

	input = dec_num;
	
	/* Check for 0--if so, print result */
	if (input == 0)
	{
		printf("*** Sign: 0 *** Biased exponent: 00000000 *** Mantissa: 00000000000000000000000\n");
		printf("***The IEEE-754 representation is: 0.000000");
		return;
	}
	
	/* Print sign: if number>0, sign is 0, else 1 */
	if (input > 0)
	{
		printf("*** Sign: 0 ");
	}
	else
	{
		printf("*** Sign: 1 ");
	}
	
	
	/* take absolute value of number before generating significand */ 
	input = fabs(input);

	/* Normalize number: */
	while (input >= 2)
	{
		input = input / 2;
		exp++;
	}
	while (input < 1)
	{
		input = input * 2;
		exp--;
	}

    /* Bias exponent by 127 and print each bit in binary with 8-iteration for-loop*/
	exp = 127 + exp; //Bias exponent
	printf("*** Biased exponent: ");
	while (i <= 7)
	{
		expbits[i] = exp % 2;
		exp = exp / 2;
		i++;
	}
	for (i = 7; i > 0; i--)
	{
		printf("%d", expbits[i]);
	}
	printf("%d", exp % 2);
	
    /* Hide 1 and print significand in binary with 23-iteration for-loop*/
	printf(" *** Mantissa: ");
    i = 1;
	input = input - 1; //hides the 1
	while (i <= 23)
	{
		input = input * 2;
		if (input >= 1)
		{
			printf("1");
			input = input - 1;
		}
		else
		{
			printf("0");
		}
		i++;
	}
	printf(" *** IEEE HEX: %x", *(unsigned int*)&dec_num);
	/* Print IEEE-754 representation */
	printf("\n");
  return;
} 

/***********************************************************************/
void ieee_dec()
{
	int ieee;
	int exp_biased;
	int exp;
	int i = 1;
	int sign_mask = 0b10000000000000000000000000000000;
	int exp_mask = 0b01111111100000000000000000000000;
	float fraction = 0;
	float norm;
	printf("Enter the IEEE-754 representation: ");
	if(scanf("%x", &ieee) != 1) { // Checks for NaN
		printf("*** Sign: - *** Special case: NaN\n");
		fflush(stdin);
		return;
	}
  
  /* prompt for IEEE-754 representation */

	/* check for special cases: 0, -0, +infinity, -infinity, NaN, 
		if so, print and return */ 
	if (ieee == 0x80000000 || ieee == 0xffffffff || ieee == 0x7fffffff || ieee < 0)
	{
		printf("*** Sign: - *** Special case: NaN\n");
		fflush(stdin);
		return;
	}
	
	if ((ieee & sign_mask) == 0) // Check sign
	{
		printf("*** Sign: + ");
	}
	else
	{
		printf("*** Sign: - ");
	}
	
	
	/* Mask biased exponent and significand from number */
	/* If biased exponent=0, number is denormalized with unbiased exponent of -126, 
		print denormalized number as fraction * 2^(-126), return */
	exp_biased = ((ieee & exp_mask) >> 23); // biased exponent
	

	/* Unbias exponent by subtracting 127 and print */
	exp = exp_biased - 127;

	float bit;
	i = 1;
	while (i < 23) //calculates fraction
	{
		bit = ((ieee >> (23-i)) & 1);
		fraction = fraction + (bit * pow(2,-i));
		i++;
	}
	printf("*** Unbiased exponent: %d", exp);
	/* Add hidden 1 and print normalized decimal number */
	norm = 1 + fraction;
	printf("*** Normalized decimal: %f ", norm);
	printf("***Decimal: %f\n", norm * pow(2,exp));
	/* Print decimal number */
	 return;
  
}

int main()
{
  /* declare local variables */
  /* until user chooses to quit, prompt for choice and select appropriate function */
   //Print menu
  int choice;
  int menu = 1;
    do{
		printf("Floating-point conversion:\n\n");
		printf("1) Decimal to IEEE-754 conversion 2) IEEE-754 to Decimal conversion 3) Exit\n");
		printf("Enter selection:"); 
		scanf("%d", &choice);
		switch(choice) {
        case 1:
        	dec_ieee();
        	continue;
        case 2:
        	ieee_dec();
        	continue;
		case 3:
			printf("*** Program Terminated Normally");
			menu = 0;
			break;
        default:
			printf("Invalid input.\n");
			fflush(stdin);
        	break;
      }
    }while(menu == 1);
  return 0;
}






