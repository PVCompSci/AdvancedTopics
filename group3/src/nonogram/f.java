package nonogram;
import java.util.*;

public class f {
	public static void main(String args[]) {
		//sample input:
		int[][] a = {{1,0,1,0,0},{1,0,0,1,1},{0,0,1,0,1},{0,1,1,1,0},{1,1,1,1,1}};

		/*
		 * stores instruc in array list to respective position
		 * first value in rows is the first instruc for the row and so on
		 */
		ArrayList<String> row = new ArrayList<String>();
		ArrayList<String> col = new ArrayList<String>();

		/*
		 * r=counter (row)
		 * s=storage comp (row)
		 * z=temp value (row)
		 * c=counter (col)
		 * l=temp value (col)
		 * p=storage comp (col)
		 */
		int r=0;
		String s;
		int z=0;
		int c=0;
		int l=0;
		String p="";

		//first nested for loop is integrate into the filling process
		for (int i = 0; i < 5; i++) {
			
			//resets vars
			s="";
			z=0;
			
			
			for (int j = 0; j < 5; j++) {
				//prints array
				System.out.print(a[i][j]+" "); 
				
				//if 1, adds to r and updates z
				if(a[i][j]==1) {
					r++;
					z=r;
				}else {
					//if r>0 adds to the string instruc (next row)
					if(r>0) {
						s+=r+" ";
						r=0;
					}
				}
			}
			//z just prevents a bunch of zeros from being added
			if(z==0) {
				row.add(0+"");
			}		
			//final add row
			if(r>0) {
				s+=r+" ";
				row.add(s);
				r=0;
			}else {
				row.add(s);
				r=0;
			}	
			//cleans up nulls because i was too lazy to do another temp
			row.removeIf(Objects::isNull);
			System.out.println();	
		}

		
		//whole process is repeated for columns, just the same thing
		for (int e= 0; e < 5; e++) {
			p="";
			l=0;
			for (int f = 0; f < 5; f++) {
				//only difference
				if(a[f][e]==1) {
					c++;
					l=c;
				}else {
					if(c>0) {
						p+=c+" ";
						c=0;
					}
				}
			}
			if(l==0) {
				col.add(0+"");
			}			
			if(c>0) {
				p+=c+" ";
				col.add(p);
				c=0;
			}else {
				col.add(p);
				c=0;
			}
			col.removeIf(Objects::isNull);
			System.out.println();	


		}			
		System.out.println(row);	
		System.out.println(col);			
	}
}
