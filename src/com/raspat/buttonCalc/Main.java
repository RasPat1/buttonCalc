/**
 *Button Calc by Rasesh Patel
 *Copyright 2012
 *
 *See ReadMe for notes
 */
package com.raspat.buttonCalc;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
    //Every time a button is hit add that digit to the input once an operator is hit
    //move input to output and save the operator
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
    
    @Override
    public void onPause() {
    	debugEvent("onPause");
//    	if (output)
//    	memory1 = output;
    	debugEvent("onPause", Float.toString(memory1));
    	super.onPause();
    }

    public void onResume() {
    	super.onResume();
    	output = memory1;
//    	display(output);
    }
    public void calculate() {
    	if(!isValid(input)) {
//    		showToast(getString(R.string.notanumber));
    		input = "";
    		return;
    	}
    	
    	switch(currentOperator) {
    	case '+': output += Float.valueOf(input);	break;
    	case '-': output -= Float.valueOf(input);	break;
    	case '*': output *= Float.valueOf(input);	break;
    	case '/': output /= Float.valueOf(input);	break;	
    	case '=': output  = Float.valueOf(input);	break;
    	default: 							   		break;
    	}
    }
 
	/**
	 * @param op
	 * Once a user hits an operator they have entered the second operand of the previous operation.  
	 * 		So how that on the screen if it's valid and set the operator for the next calculation.
	 * If the no input has been entered but user would like to use the value on the 
	 * 		screen as the operand move the displayed value to the output and use the operator they just hit
	 * 		and the input they are about to put in to do a calculation. 
	 * If the user has entered something in and hit an operator evaluate the last thing they entered before reassigning the operator
	 * 
	 * 
	 */
	public void onClickOp(char op) {

		if (input == "" && currentOperator == '=') {
			output = memory1;
		} else if (input != "") {
			calculate();
			display(output);
			input = "";
		}
		currentOperator = op;
	}
    
	public void onClickEquals(View v) {

		calculate();
		display(output);
		memory1 = output;
		output = 0;
		currentOperator = '=';
		input = "";
	}
    
	public void onClick(View v) {	
		switch(v.getId()) {
		case R.id.button0: input +="0"; break;
		case R.id.button1: input +="1"; break;
		case R.id.button2: input +="2"; break;
		case R.id.button3: input +="3"; break;
		case R.id.button4: input +="4"; break;
		case R.id.button5: input +="5"; break;
		case R.id.button6: input +="6"; break;
		case R.id.button7: input +="7"; break;
		case R.id.button8: input +="8"; break;
		case R.id.button9: input +="9"; break;
		case R.id.buttondecimal: input += !containsDecimal() ? "." :""; break; 
		default: Log.d("debug", "The default onClick has been triggered"); break;
		}
		display(input);
	}
	
	public void onClickOp(View v) {
		switch(v.getId()) {
		case R.id.buttonadd: 		onClickOp('+'); break;
		case R.id.buttonsubtract: 	onClickOp('-'); break;
		case R.id.buttonmultiply: 	onClickOp('*'); break;
		case R.id.buttondivide: 	onClickOp('/'); break;
		default: Log.d("debug", "The default onClickOp has been triggered"); break;
		}
	}
	
	public void display(String s) {
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(s);
	}

	public void display(float f) {
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(String.valueOf(f)); 	// Doesn't need to be error checked
										// right?
	} 									// Maybe check for length or emptiness? Nah.

	/**
	 * @param s
	 * @return 
	 * If the input is empty, or only a decimal or just not a number somehow the string is not valid.
	 * This does not check for multiple decimals since that is checked when decimal button is pressed.
	 */
	public boolean isValid(String s) {
		if (s == "" || s.length() == 0 || (s.length() == 1 && s.contains("."))
				|| (Float.valueOf(s)).isNaN()) {
			return false;
		}
		return true;
	}

    public boolean containsDecimal() {
    	if(input.contains(".")) {
    		showToast(getString(R.string.duplicatedecimal));
    		return true;
    	}
    	return false;
    }
    
	public void showToast(String message) {
		Toast.makeText(this, message ,Toast.LENGTH_LONG).show();
	}
    
    private void debugEvent(final String method) {
    	long ts = System.currentTimeMillis();
    	Log.d("buttonCalcDebug", " *** " + method + " " + this.getClass().getName() + " " + ts);
    }
    private void debugEvent(final String method, final String message) {
    	long ts = System.currentTimeMillis();
    	Log.d("buttonCalcDebug", " *** " + method + " " + message + " " + this.getClass().getName() + " " + ts);
    }
}
