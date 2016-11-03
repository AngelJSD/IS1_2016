package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class CalculadoraApplication {
	
	public abstract class calculator{
		public String texto;
		public float x;
		public float y;
		
		public calculator(float A, float B, String op){
			x=A;
			y=B;
			texto = x + " " + op + " " + y + " = ";
		}
		public abstract float calculate();
	}
	
	public class suma extends calculator{
		
		public suma(float A, float B, String op) {
			super(A, B, op);
		}

		public float calculate(){
			return x + y;
		}

	}
	public class resta extends calculator{
		public resta(float A, float B, String op) {
			super(A, B, op);
		}

		public float calculate(){
			return x - y;
		}
	}
	public class multiplicacion extends calculator{
		public multiplicacion(float A, float B, String op) {
			super(A, B, op);
		}

		public float calculate(){
			return x * y;
		}
	}
	public class division extends calculator{
		public division(float A, float B, String op) {
			super(A, B, op);
		}

		public float calculate(){
			return x / y;
		}
	}
	
	public calculator identify(String op, float A, float B){
		switch(op){
		   	case "suma":
		   		return new suma(A,B,"+");
		   	case "resta":
		   		return new resta(A,B,"-");
		   	case "multiplicacion":
		   		return new multiplicacion(A,B,"*");
		   	case "division":
		   		return new division(A,B,"/");
		   	default:
		   		return null;
		}
	}
	
    @RequestMapping("/")
    @ResponseBody
    String home(){
    	return "CALCULADORA \n Operaciones disponibles:\nsuma\nresta\nmultiplicacion\ndivision\nEjemplo de uso: /calc?a=1&b=2&op=division";
    }
    
	@RequestMapping("/calc")
    @ResponseBody
    String calc(@RequestParam String a, @RequestParam String b, @RequestParam String op) {
    	
    	float x = 0;
    	float y = 0;
    	float result = 0;
    	try{
    		x = Float.parseFloat(a);
        	y = Float.parseFloat(b);
    	}catch( IllegalArgumentException e ){
    		return "Error valores ingresados inválidos";
    	}
    	
    	calculator calc = identify(op, x, y);
    	
    	if ( calc != null ){
    		try {
    			result = calc.calculate();
			} catch (ArithmeticException e) {
				return "Error resultado indefinido";
			}
    		if(Double.isInfinite(result)){
    			return "Error resultado indefinido";
    		}
			return calc.texto + result;
		}
		return "Error operacion no valida";
  	
    	
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CalculadoraApplication.class, args);
    }
}