/**
 *Button Calc  by Rasesh Patel
 *
 *This calculator has buttons on it.  Thereby forcibly limiting the 
 *inputs available to the user in contrast to the program simpleCalc.
 *This reduces the number of scenarios that need to be 'caught' for 
 *force close protection.  
 *
 *		---- Poor Implementations ----
 *FIGURE OUT HOW TO USE STATIC VARIABLES PROPLERLY! i'm nearly certain
 *	that this can be and should be done without using static variables.  
 *	the only problem is that you never quite figured out how to do it right.
 *	learn.
 *The buttons each have their own method.  Tons of repetition.. Disgusting
 *Can I use ENums anywhere here to clean up the code?
 *identical code written in each method for operators... Fickle
 *UI does not fill screen
 *Names of the variables suck... Really change them.  Like now.
 *Did not install proper debug procedures.  Next time use Logcat
 *	and for god sakes figure out how to use the step functions, breakpoints, and 
 *	variable and expression monitors you idiot!
 *		
 *
 *		---- Questions ----
 *
 *Where are the potential error points?
 *		turning from a string to an integer 
 *		Float.ValueOf(String input)
 *
 *		Trying to do operations (+,-,*,'/')math with misbehaving Floats
 *
 *		handling behavior that allows user to use current output as input
 *		for next operation.
 *
 *Solution for both problems:
 *		Originally used .isNaN to stop any misbehaved numbers from being used in 
 *		the math section however this still required them to be converted into floats.
 *		Instead, manually check each character of the string to ensure that only
 *		digits and a decimal are present before converting into a float.
 *		This is the same check that was necessary for SimpleCalc...
 *
 *		Also by checking the validity of the number before operating on them we exclude
 *		force closes resulting from impossible operations.
 *
 *		---- Analysis ----
 *		Similar checking of the input strings are required in both SimpleCalc and ButtonCalc
 *		  How can we avoid this inelegant step?  Implement solution in next iteration.  
 *		  Since poor numbers can only be created by adding multiple decimals add 
 *		  field containsDecimal.  This solution forces checking of the string each time 
 *		  the decimal key is hit and only when the decimal key is hit.  
 *		Possible implementation option is have the textfield really be the place where you get the values form
 *		  afterall it is what the user thinks is happening coding it like this will be more readable and lifelike
 *		  basically when performing any operations the program would take the value on the screen as one of the operands
 *		  the check for validity would be done at the button press before updating to the screen and notify the user then
 *		
 *
 *		---- Revisions ----
 *		General
 *		-General Polish
 *		-Home Screen Icon
 *		-Menu buttons with About page at the very least
 *		Update UI
 *		-Add landscape feature
 *		-Implement multiple screen compatibility
 *		-Full screen pad of buttons
 *		-Adjust layout and button sizes to be more natural and realistic
 *		-Tones and textures
 *		Features
 *		-Memory, Clear, Clear memory
 *		-Styling options?
 *		Mathematics
 *		-properly implement rounding methods rather than using defaults from ValueOf
 *		-negative numbers
 *		-Operations
 *		-Graphing
 *		-Etcetera
 *
 *		-- Explorations --
 *		Post Fix and Prefix calculators why they arose and how they are coded..
 *			Are these answers related?
 *		Typical calculator designs.   Multi-paradigm
 *		-What generalizations can be made about the language based on the necessary 
 *			structure of the calculators?
 *		WTF is an ENUM and how can i use it to DRY the code?
 *		
 *		---- Tips ----
 *		Try to develop more incrementally
 *		Standardize your comment formatting.  Use Javadoc notation for each method properly
 *
 *		---Notes-----
 *		Started using Git with this Application to test out and learn Git.  9-9-12 5:51AM
 */
package com.raspat.buttonCalc;

/**
 * Current DEBUG
 * catch if string = "." or ".0"
 * 
 * 
 */
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    //Every time a button is hit add that digit to the input once an operator is hit
    //move input to running total ad save the operator
    float output = 0;
    String input = "";
    char currentOperator = '0';				//Zero operator serves as start identifier
    float memory1 = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void display(String s) {
    	//update display every time input is changed
    	TextView tv = (TextView) findViewById(R.id.textView1);
    	tv.setText(s);
    }
    
    public void display(float f) {
    	TextView tv = (TextView) findViewById(R.id.textView1);
    	tv.setText(String.valueOf(f));							//Doesn't need to be error checked right?
    }															//Maybe check for length or emptiness?  Nah.
    /**
     * Calculate method
     * First check string new number for invalid characters
     * Digits are values 48-57, and a decimal is 46
     */
    public boolean isValid(String s) {
    	if(s.length() == 0 || (s.length() == 1 && s.contains("."))) {
    		return false;
    	} 
    	return true;
    }
    
    public void calculate() {
    	if(!isValid(input)) {
    		return;
    	}
    	//Extra and possibly unnecessary error check. Just for safety
    	if((Float.valueOf(input)).isNaN()) {
    		input = "";
    		showToast("@string/notanumber");
    		return;
    	} else if(input =="") {
    		return;
    	}
    	
    	switch(currentOperator) {
    	case '+': output += Float.valueOf(input);
    		break;
    	case '-': output -= Float.valueOf(input);
    		break;
    	case '*': output *= Float.valueOf(input);
			break;
    	case '/': output /= Float.valueOf(input);
    		break;	
    	case '=': output = Float.valueOf(input);
    		break;
    	}
    }
    
    /**
     * 
     * @param v
     * 
     * POssible valid input methods States:
     * a + a + a + a...				show output after each add
     * a + a =						show output after =
     * a + a = + a					allow last output to be used as next input
     * Blank calculator everything set to default values
     *	If hitting the operator without any numbers pressed 
     *		do nothing (or better yet display a message) or work with default value fo zero if you want
     * 	If input has been entered and operator has been pressed
     * 		move input to total and set operator to plus clear input
     * Has just received an output and wants to enter addition using current output as operand
     * 		
     * 
     */
    
	public void onClickAdd(View v) {
		onClickOp('+');
	}

	public void onClickSubtract(View v) {
		onClickOp('-');
	}

	public void onClickMultiply(View v) {
		onClickOp('*');
	}

	public void onClickDivide(View v) {
		onClickOp('/');
	}
    
    public void onClickOp(char op) {
    	//TODO clean up conditionals
    	if(currentOperator == '0' && input == "") {
    		return;
    	}  else if(currentOperator == '=' && input == "") {
    		output = memory1;
    		currentOperator = op;
    	}  else if(input == "" || !isValid(input)) {
    		return;
    	}  else 	
    		currentOperator = op;
    		calculate();
    		display(output);
    		input = "";
    }
    
    public void onClickEquals(View v) {
    	calculate();
    	display(output);
    	memory1 = output;
    	output = 0;
    	currentOperator = '=';
    	input = "";
    }
    public void onClickDecimal(View v) {
    	if(input.contains(".")) {
    		showToast("@string/duplicatedecimal");
    		return;
    	}
    	input += ".";
    	display(input);
    }
    
	public void showToast(String message) {
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();
//		Toast.makeText(this, message ,Toast.LENGTH_LONG).show();
	}

	/**
	 * Figure out how to dry this code out
	 * @param v
	 */
	
	public void onClickOne(View v) {
		input += "1";
		display(input);
	}

	public void onClickTwo(View v) {
		input += "2";
		display(input);
	}

	public void onClickThree(View v) {
		input += "3";
		display(input);
	}

	public void onClickFour(View v) {
		input += "4";
		display(input);
	}

	public void onClickFive(View v) {
		input += "5";
		display(input);
	}

	public void onClickSix(View v) {
		input += "6";
		display(input);
	}

	public void onClickSeven(View v) {
		input += "7";
		display(input);
	}

	public void onClickEight(View v) {
		input += "8";
		display(input);
	}

	public void onClickNine(View v) {
		input += "9";
		display(input);
	}

	public void onClickZero(View v) {
		input += "0";
		display(input);
	}


}
