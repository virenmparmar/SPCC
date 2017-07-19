import java.lang.*;
import java.util.*;
import java.io.*;
//import java.lang.time.*;


class Lax{
	
static HashMap<String, Integer> kt = new HashMap<String, Integer>();  //Keyword table
static HashMap<String, Integer> ot = new HashMap<String, Integer>();	//Operator table
static HashMap<String, Integer> sst = new HashMap<String, Integer>();	//Special Symbol Table
static HashMap<String, Integer> ft = new HashMap<String, Integer>();	//Function Table
static HashMap<String, Integer> st = new HashMap<String, Integer>();	//Symbol/v table
static HashMap<String, Integer> lt = new HashMap<String, Integer>();	//Literal Table
static StringTokenizer token;
static String line;
static int stkey = 1;
static int ltkey = 1;
	
	
	
	public static void main(String args[])
	{

			long startTime = System.nanoTime();
		Scanner sc = new Scanner(System.in);
		
		//Calling Function to create 
		createTables();		
		
		System.out.println("Enter the file name");
		
		String input= sc.nextLine();
		try
		{
		//input = "C:\Users\exam\Desktop\Lax.c";	
		FileInputStream fis = new FileInputStream(input);
        //BufferedReader br = new Bu
        Scanner scc = new Scanner(fis);
		
		while(scc.hasNextLine())
		{
            line =scc.nextLine();
			laxicalAnalyse(line);
		}
		
		}
		
		catch (Exception e)
		{
			System.out.println(e);
		}
		displayTables();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

        System.out.println(duration);
		
	}



	public static void createTables()
	{
	
		//Creating Keyword Table
		kt.put("break",1);
		kt.put("case",2);
		kt.put("char",3);
		kt.put("else",4);
		kt.put("float",5);
		kt.put("for",6);
		kt.put("if",7);
		kt.put("int",8);
		kt.put("void",9);
		kt.put("while",10);
				
		//Creating OPerator TAble
		ot.put("+",1);
		ot.put("-",2);
		ot.put("*",3);
		ot.put("/",4);
		ot.put("%",5);
		ot.put("=",6);
		ot.put("<",7);
		ot.put(">",8);
		
		//Creating Special Symbol Table
		sst.put("{",1);
		sst.put("}",2);
		sst.put(",",3);
		sst.put(".",4);
		sst.put("[",5);
		sst.put("]",6);

		//Creating Function Table
		ft.put("main",1);
		ft.put("printf",2);
		ft.put("scanf",3);
	}
		
	public static void displayTables()
	{
		System.out.println(kt);   //Displays Keyword Table
		System.out.println(ot);     //Displaying operators
		System.out.println(sst);	//Displaying Special Symbols Table
		System.out.println(ft);		//Displaying Function Table
		System.out.println(st);		//Displaying Sympbol(variable) Table
		System.out.println(lt);		//Displaying Literal Table
		
	}
	
	
	public static void laxicalAnalyse(String ip) throws Exception
	{
		/* String ch="";
		for(int i,j;i<=ot.size(),j<=sst.size();i++,j++)
		{
			ch= ch.concat(ot.get);
		} */
		
		
		
		StringTokenizer tk = new StringTokenizer(")");
		token = new StringTokenizer(ip," ,+-*/;()=",true);
		outer:
		while(token.hasMoreTokens())
		{
			String x = token.nextToken();
			int key;
			
			if(x=="/" && token.nextToken() =="/")
			{
				System.out.print("This is comments");
				break outer;
			}
			else if(x=="/" && token.nextToken() == "*")
			{
				
			}
			if(kt.containsKey(x))
			{
				key = (kt.get(x));
				System.out.print("key#"+key+"  ");
			}
			else if(ot.containsKey(x))
			{
				key = (ot.get(x));
				System.out.print("operator#"+key+"  ");
			}
			else if(sst.containsKey(x))
			{
				key = (sst.get(x));
				System.out.print("splsym#"+key+"  ");
			}
			else if(ft.containsKey(x))
			{
				key = (ft.get(x));
				System.out.print("function#"+key+"  ");
				do
				{
					x = token.nextToken();
				}while(token!=tk);
			}
			else
			{
				char in[] = x.toCharArray();
				if (Character.isLetter(in[0]))
				{
					if(st.containsKey(x))
					{
						key = (st.get(x));
						System.out.print("variable#"+key+"  ");
					}
					else
					{
						st.put(x,stkey);
						stkey++;
					}
				}
				/*else(Character.isDigit(in[0]))
				{
					if(lt.containsKey(x))
					{
						key = (lt.get(x));
						System.out.print("literal#"+key+"  ");
					}
					else
					{
						lt.put(x,ltkey);
						ltkey++;
					}
					
				}*/
			}
			System.out.println();
			
			
		}
		
		
	}


}





