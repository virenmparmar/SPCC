import java.lang.*;
import java.util.*;

class RDP
{
	//s->a
	
	public static int current=0, ptr;
	public static String in;
	public static int len;
	public static char[] input;
	public static void main(String args[])
	{
		System.out.println("Enter the input string");
		Scanner sc = new Scanner(System.in);
		
		String in = sc.nextLine();
		input = in.toCharArray();
		
		if(S())
		{
			System.out.println("String is valid");
		}
		else 
		{
			System.out.println("The String is invalid");
		}
		
	}
	
	public static boolean S()
	{
		boolean flag = false;
		if(input[current]=='a')
		{
			ptr = current;
			current++;
			//return false;
		}
			if(A()==fa)
			{
				if(B())
				{
					flag = true;
				}
				else 
					flag = false;
			}
		}
		else 
			flag = false;
		
		return flag;
	}
	
	public static boolean A()
	{
		boolean flag = false;
		if(input[current]=='b')
		{
			//flag = true;
			ptr = current;
			current++;
			if(B())
			{
				flag = true;
			}
			else
				current = ptr;
				//flag = false;
		} 
		else if(input[current]=='b')
		{
			current++;
			flag = true;
		} 
		else
			flag = false;
		
		return flag;
	}
	
	public static boolean B()
	{
		boolean flag;
		if(input[current]=='c')
			flag = true;
		else 
			flag = false;
		
		return flag;
	}
}