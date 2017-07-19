import java.io.*;
import java.lang.*;
import java.util.*;
//assembler direcive 
//static StringTokenizer token;
/*public static String[] arr; //arr is the char array of each line after spliting
public static Pot[] p = new Pot[10];
public static Mot[] m = new Mot[20];
public static Literal[] l = new Literal[40];
public static Symbol[] s = new Symbol[40];
public static int key; //key is for different cases of POT table*/
//public static int LC=0, lkey=0,skey=0; //lkey and skey to keep a track of number of symbols and literals,LC is the location counter
class Mot
{
	String machine;
	int opcode;
	int length;
	String intstype;
	Mot(String m, int o, int l, String i)
	{
		this.machine = m;
		this.opcode = o;
		this.length = l;
		this.intstype = i;
	}
	
}

class Pot
{
	String pseudo;
	String function;
	Pot(String p, String f)
	{
		this.pseudo = p;
		this.function = f;
	}
	
}

class Symbol
{
	String symbol;
	int value;
	int slength;
	boolean reloc;
	Symbol(String s,int v,int l,boolean r)
	{
		this.symbol = s;
		this.value = v;
		this.slength = l;
		this.reloc = r;		
	}
}

class Literal
{
	String symbol;
	int value;
	int llength;
	boolean reloc;
	Literal(String s, int v, int l, boolean r)
	{
		this.symbol = s;
		this.value = v;
		this.llength = l;
		this.reloc = r;		
	
	}
}

class BaseTable
{
	int bnum;
	boolean use;
	int value
	BaseTable(int b,boolean u,int v)
	{
		this.bnum = b;
		this.use = u;
		this.value = v;
	}
}


class LineDetails
{
	int lineno,lc;
	String instr,data;
	LineDetails(int lineno, int lc, String instr, String data)
	{
		this.lineno  = lineno;
		this.lc = lc;
		this.instr = instr;
		this.data = data;
	}
}

public class Twopass
{
	public static Pot[] ptab = new Pot[10];
	public static Mot[] mtab = new Mot[20];
	public static Literal[] ltab = new Literal[40];
	public static Symbol[] stab = new Symbol[40];
	public static BaseTable[] btab = new BaseTable[16];
	public static ArrayList<LineDetails> ld = new ArrayList<LineDetails>();
	public static int LC=0, lkey=0,skey=0,lineno=0;
	public static void main(String args[])
	{
		
		
		Scanner sc = new Scanner(System.in);
		
		ptab[0] = new Pot("START","start()");
		ptab[1] = new Pot("DC","dc()");
		ptab[2] = new Pot("DS","ds()");
		ptab[3] = new Pot("LTORG","ltorg()");
		ptab[4] = new Pot("END","end()");
		ptab[5] = new Pot("EQU","equ()");
		ptab[6] = new Pot("DROP","drop()");
		ptab[7] = new Pot("USING","using()");

		
		mtab[0] = new Mot("L",58,4,"RX");
		mtab[1] = new Mot("ST",50,4,"RX");
		mtab[2] = new Mot("A",5,4,"RX");
		mtab[3] = new Mot("C",59,4,"RX");
		mtab[4] = new Mot("D",51,4,"RX");
		mtab[5] = new Mot("LA",41,4,"RX");
		mtab[6] = new Mot("SR",17,2,"RR");
		mtab[7] = new Mot("AR",15,2,"RR");
		mtab[8] = new Mot("BNE",47,4,"RX");
		mtab[9] = new Mot("BR",7,2,"RR");
		for(int i=0;i<<16;i++)
		{
			btab[i]=new BaseTable(i,false,0);
		}
		
		//String input = sc.nextLine();
		display();
		try
		{
		File file = new File("C:\\Users\\Umang\\Desktop\\AssemblyProgram2.txt");
        FileReader fil = new FileReader(file);
		BufferedReader reader =  new BufferedReader(fil);
		System.out.println("12");

		String line=null;
		while((line = reader.readLine()) != null)
		{
            //line =scc.nextLine();
			//StringTokenizer st = new StringTokenizer(line," ",false);
			String lin[] = line.split(" ");
			String label="";
			String operand="";
			String mnemonic="";
				System.out.println("34");
			if(lin.length==3)
			{
				label=lin[0];
				mnemonic=lin[1];
				operand=lin[2];
			}else if(lin.length==2)
			{
				mnemonic=lin[0];
				operand=lin[1];
			}
			else
			{
				mnemonic=lin[0];
			}
			System.out.println("78");
			eval(label,mnemonic,operand);
			System.out.println("56");
		}
		}
		catch(Exception e)
		{
		System.out.println("a"+e);
		}
		display();
		
		try
		{
		File file = new File("C:\\Users\\Umang\\Desktop\\AssemblyProgram2.txt");
        FileReader fil = new FileReader(file);
		BufferedReader reader =  new BufferedReader(fil);
		System.out.println("12");

		String line=null;
		while((line = reader.readLine()) != null)
		{
            //line =scc.nextLine();
			//StringTokenizer st = new StringTokenizer(line," ",false);
			String lin[] = line.split(" ");
			String label="";
			String operand="";
			String mnemonic="";
				System.out.println("34");
			if(lin.length==3)
			{
				label=lin[0];
				mnemonic=lin[1];
				operand=lin[2];
			}else if(lin.length==2)
			{
				mnemonic=lin[0];
				operand=lin[1];
			}
			else
			{
				mnemonic=lin[0];
			}
			if(lineno==0 && mnemonic!="START")
			{
				System.out.println("Error: Start not on line one");
			}
			System.out.println("78");
			evalpass2(label,mnemonic,operand);
			lineno++;
			System.out.println("56");
		}
		}
		catch(Exception e)
		{
		System.out.println("a"+e);
		}
		
	}
	
	public static void evalpass2(String l, String m, String o)
	{
		//String data;
		BaseTable tempbase;
		if(findinPOT(m)!=null)
		{
			if(m.equals("START"))
			{
				LC = Integer.parseInt(o);
			}
			else if(m.equals("USING"))
			{
				String op[] = o.split(",");
				int x = Integer.parseInt(op[1]);
				btab[x].use=true;
				if(op[0]=="*")
				{
					btab[x].value = LC;
				}
				else
				{
					btab[x].value= Integer.parseInt(op[1]);
				}
			}
			else if(m.equals("DROP"))
			{
				String op[] = o.split(",");
				int x = Integer.parseInt(op[1]);
				btab[x].use=false;
			}
			else if(m.equals("DC"))
			{
				String dataop = o.substring(o.indexOf("'")+1,o.lastIndexOf("'"));
				String numbers[] = dataop.split(",");
				for(String x : numbers)
				{
					ld.add(new LineDetails(lineno,LC,x,""));
					LC += 4;
				}
			}
			else if (m.equals("DS"))
			{
				int n = Integer.parseInt(operand.substring(0,operand.length()-1));
				ld.add(new LineDetails(lineno,LC,"",""));
				LC = LC +(n*4);
			}
			else if (m.equals("LTORG"))
			{
				for(Literal l: ltab)
				{
					while(LC%8==0)
						LC++;
					ld.add(new LineDetails(lineno,l.value);
					LC=LC+4;
				}
			}
			
		}	
	else
		{
			/*
			String machine;
	int opcode;
	int length;
	String intstype;*/
			Mot temp = findMOT(m);
			if(temp!=null)
			{
				
				if(temp.machine=="BNE")
				{
					
					int regno = getregister()
				}
				else if(temp.machine=="BR");
				{
					ld.add(new LineDetails(lineno,LC,"BC","15,"o));
					LC += 4;
				}
				else if(temp.intstype=="RR")
				{
					String operands[] = o.split(",");
					String data="";
					for(String q:operands)
					{
						try
						{
							int val = Integer.parseInt(eval(q));
							data = data + Integer.toString(val) +",";
						}
						catch(Exception e)
						{
							if(q.indexOf("="==-1)
							{
								Symbol temps = findST(q);
								data = data + Integer.toString(temps.value) + ",";
							}
							else
							{
								Literal temps = findLT(q);
								data = data + Integer.toString(temps.value) + ",";
							}
						}
					}
					ld.add(new LineDetails(lineno,LC,temp.opcode,temps.value);
				}
				else if(temp.intstype=="RX")
				{
					String operands[] = o.split(",");
					String data="";
					for(String q:operands)
					{
						try
						{
							int val = Integer.parseInt(eval(q));
							data = data + Integer.toString(val) +",";
						}
						else
						{
							if(q.indexOf("="==-1)
							{
								Symbol temps = findST(q);
								data = data + Integer.toString(temps.value) + ",";
							}
							else
							{
								Literal temps = findLT(q);
								data = data + Integer.toString(temps.value) + ",";
							}
						}
					}
					operands = o.split(",");
					int val = findST(o[0]);
					int val1;
					try
					{
						val1 = findST(o[1]).value;
					}
					catch
					{
						val1 = findLT(o[1]).value;
					}
					tempbase = getregister(Integer.parseInt(o[1]));
					data = Integer.toString(val) + "," + tempbase.value-Integer.toString(val1); +"(0,"+ tempbase.bnum;
					ld.add(new LineDetails(lineno,LC,temp.opcode,data);
				}
			}
		}	
	}
	
	public static void eval(String l,String m, String o)
	{
		System.out.print(l+m+o);
		Pot cpot = findinPOT(m);
		if(cpot!=null)
		{
			System.out.println("tr");
			if(cpot.pseudo=="START")
			{
				if(l!="")
				{
					LC=Integer.parseInt(o);
				}
				else
				{
					stab[skey]=new Symbol(l,LC,1,true);
					skey++;
				}
			}
			else if(cpot.pseudo=="DC")
			{
				//int x = String.countOccurrencesOf(o, ",");
				int x = new StringTokenizer(o, ",",false).countTokens()-1;
				LC=LC+((x+1)*4);
			}
			else if(cpot.pseudo=="DS")
			{
				int x = Integer.parseInt(o.substring(0,o.indexOf("F")));
				LC=LC+(x*4);
			}
			else if(cpot.pseudo=="LTORG" || cpot.pseudo=="END")
			{
				while(LC%8!=0)
				{
					LC++;
				}
				for(Literal li:ltab)
				{
					if(li.value==-2)
					{
						li.value=LC;
						li.llength=4;
						li.reloc=true;
						LC=LC+4;
					}
				}
			}
			else if(cpot.pseudo=="USING" || cpot.pseudo=="DROP")
			{
				System.out.print(" ");
			}
			else if(cpot.pseudo=="EQU")
			{
				Symbol s = findST(l);
				if(s==null && (o.indexOf("*")==-2))
				{
					stab[skey]=new Symbol(l,LC,1,false);
				}
				else if(s==null && (o.indexOf("*")!=-1))
				{
					stab[skey]=new Symbol(l,LC,1,true);
				}
			}
		}
		
		System.out.println("asd");
		if(cpot == null)
		{
		   Mot mpot = findMOT(m);
		if(mpot!=null)
		{
			LC=LC+mpot.length;
		}
		}
		
		if(o.indexOf("=")!=-1)
		{
			String lit = o.substring(o.indexOf("=")+1);
			ltab[lkey]= new Literal(lit, -2, -2, false);
		}
		if(m!="EQU")
		{
			System.out.println(l+"\t"+m+"\t"+o);
		}
		
	}
	
	
	public static Pot findinPOT(String q)
	{
		for(Pot e:ptab)
		{
			if(q.equals(e.pseudo))
			{
				return(e);
			}
		}
		return(null);
	}
	
	public static Mot findMOT(String q)
	{
		for(Mot e:mtab)
		{
			if(q.equals(e.machine))
			{
				return(e);
			}
		}
		return(null);
	}
	public static Symbol findST(String q)
	{
		for(Symbol e:stab)
		{
			if(q.equals(e.symbol))
			{
				return(e);
			}
		}
		return(null);
	}
	
	public static Literal findLT(String q)
	{
		for(Literal e:ltab)
		{
			if(q.equals(e.symbol))
			{
				return(e);
			}
		}
		return(null);
	}
	
	public static void display()
	{
		for(Pot e:ptab)
		{
			System.out.println(e.pseudo);
		}
		for(Mot e:mtab)
		{
			System.out.println(e.machine+"\t"+e.opcode+"\t"+e.length+"\t"+e.intstype);
		}
		for(Symbol e:stab)
		{
			System.out.println(e.symbol+"\t"+e.value+"\t"+e.slength+"\t"+e.reloc);
		}
		for(Literal e:ltab)
		{
			System.out.println(e.symbol+"\t"+e.value+"\t"+e.llength+"\t"+e.reloc);
		}
	}
	
	public static BaseTable getregister(int x)
	{
		int i,min=0;
		BaseTable send;
		for(BaseTable b:btab)
		{
			if(b.use==true)
			{
				i=b.value-x;
				if(i<min)
				{
					min = i;
					send = b;
				}
			}
		}
		return send;
	}
	
}
