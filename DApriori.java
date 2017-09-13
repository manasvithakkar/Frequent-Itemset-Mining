
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class DApriori {
	public static Set<Integer> abcd = new HashSet<>(); 
	   public static String FileName;
	   public static List<int[]> frequentOne_set = new ArrayList<int[]>();
	   public static List<int[]> pre_level_frequent_set = new ArrayList<int[]>();
	   public static HashMap <String,Integer> hm = new HashMap <String,Integer>();
	   public static HashMap <Integer,Integer> single_sup = new HashMap <Integer,Integer>();
	   public static int minsup;
	   
	   public static int k;
	   public static int level=1;
	   
	   public static int number_of_transactions ;
	   	   
	   public static HashMap <Integer,List<Integer>> One_set = new HashMap <Integer,List<Integer>>();
	   public static HashMap <List<Integer>,List<Integer>> multiSet = new HashMap <List<Integer>,List<Integer>>();
	   
	   
	   /* Methods */
	   
	   public static Object getKeyFromValue( Object value) {
		    for (Object o : hm.keySet()) {
		      if (hm.get(o).equals(value)) {
		        return o;
		      }
		    }
		    return null;
		  } 
	   
	   
	   public static void Candidate_gen(FileWriter writer) throws IOException
		  {
			   
		    HashMap<Integer, int[]> tempCandidates = new HashMap<Integer, int[]>(); //temporary candidates
		    int currentSizeOfItemsets = frequentOne_set.get(0).length;
		    boolean isValid = true;
		    int counter =0;
		    Set<Integer> s11 = new HashSet<Integer>();
		    for(int i=0; i<frequentOne_set.size()-1; i++)
		    {
		      for(int j=i+1; j<frequentOne_set.size(); j++)
		      {
		    	int flag = 0;
		        int[] X = frequentOne_set.get(i);
		        int[] Y = frequentOne_set.get(j);
		        assert (X.length==Y.length);                         //make a string of the first n-2 tokens of the strings
		        int [] newCand = new int[currentSizeOfItemsets+1];
		        if (level == 2){
					 newCand[0] = X[0];
					 newCand[1] = Y[0];
					 Arrays.sort(newCand);
	                 tempCandidates.put(counter++,newCand);
					   
				   }
		        else{
		        for(int s=0; s<X.length-1; s++) 
		        {
		                    
		                    if(X[s]!= Y[s]){
		                    	flag =1;
		                    }
		                      
		        }
		        if(flag==0){
		        	for(int x=0;x<newCand.length-1;x++){
		        		newCand[x]=X[x];
		        	}
		        	newCand[X.length] = Y[Y.length-1];
		        	Arrays.sort(newCand);
                 tempCandidates.put(counter++,newCand);
		        }
		        }
		      }
		    }
		    frequentOne_set  = new ArrayList<int[]>(tempCandidates.values());
		    List<Set<Integer>> tempSet = new ArrayList<Set<Integer>>();
		    for(int [] array : frequentOne_set )
		    {
		        int n = array.length;
		        for(int j=0;j<n-2;j++){
		        	
		        	
		        	for(int i=0;i<n;i++){
		        		if(i!=j){
		        			s11.add(array[i]);
		        			}
		        }
		        	 if(s11.size()==n-1)
		        	 {
		        		
		        		 for(int[] aj :pre_level_frequent_set)
		        		 {
		        			 Set<Integer> pre_set = IntStream.of(aj).boxed().collect(Collectors.toSet());
		        			 if(pre_set.containsAll(s11))
		        			 {
		        				 
		        				 isValid=true;
		        				 
		        				 break;
		        			 }
		        			 else
		        			 {
		        				 isValid=false;
		        			 }
		        			 
		        		 }
		        		 
		        		 
		        		 }
		        	 
		        	 s11.clear();
		        	 if(isValid == false){
		       			 break;
		        		 } 
		        
		        
		        
		        }
		        if(isValid==false)
		        {
		        	
		        }
		        else
		        {
		        	List<Integer> abc = new ArrayList<>();
		        	for(int i=0;i<array.length;i++){
		        		abc.add(array[i]);
		        	}
		        	//Set<Integer> newS = new HashSet<Integer>(abc);
    			 //Set<Integer> newS = IntStream.of(array).boxed().collect(Collectors.toSet());
		        	tempSet.add(new HashSet<Integer>(abc));
		        }  
		        }
			    
		        calculateFrequentItemsets(tempSet,writer);
		        
		        
		        pre_level_frequent_set= frequentOne_set;
		      
		        level +=1;
		        	 
		        }
	   
	   
	   public static void calculateFrequentItemsets(List<Set<Integer>> tempSet, FileWriter writer) throws IOException 
	    {
			List<int[]> finalSet = new ArrayList<>();
			//List<Integer> myArray = new ArrayList<>();
			if(level==2){
			for(Set<Integer>s : tempSet){
				List<Integer> myArray = new ArrayList<Integer>(s);
				List<Integer> l1 = One_set.get(myArray.get(0));	
				List<Integer> l2 = One_set.get(myArray.get(1));
				List<Integer> first = new ArrayList<>();
				List<Integer> second = new ArrayList<>();;
				for(int i=0;i<l1.size();i++){
					first.add(l1.get(i));
				}
				for(int i=0;i<l2.size();i++){
					second.add(l2.get(i));
				}
				first.retainAll(second);
				if(first.size()>=minsup){
					multiSet.put(myArray, first);
					int [] arr = new int[myArray.size()];
					for(int i=0;i<myArray.size();i++){
						arr[i] = myArray.get(i);
					}
					finalSet.add(arr);
				}
			}
			}
			
			else{
				HashMap<List<Integer>,List<Integer>> tempHash = new HashMap<>();
				for(Set<Integer>s : tempSet){
					
					List<Integer> myArray = new ArrayList<>(s);
					ArrayList<Integer>subArray = new ArrayList<>(myArray.subList(0,myArray.size()-1));
					List<Integer> l1 = multiSet.get(subArray);	
					List<Integer> l2 = One_set.get(myArray.get(myArray.size()-1));
					List<Integer> first = new ArrayList<>();
					List<Integer> second = new ArrayList<>();;
					for(int i=0;i<l1.size();i++){
						first.add(l1.get(i));
					}
					for(int i=0;i<l2.size();i++){
						second.add(l2.get(i));
					}
					first.retainAll(second);
					if(first.size()>=minsup){
						tempHash.put(myArray, first);
						int [] arr = new int[myArray.size()];
						for(int i=0;i<myArray.size();i++){
							arr[i] = myArray.get(i);
						}
						finalSet.add(arr);
					}
				
					
					
				}
				multiSet.clear();
				multiSet = tempHash;
				//tempHash.clear();
				
			}
		
		  
		  frequentOne_set = finalSet;
		  //finalSet.clear();
		  if(level>=k){
			  for(int [] arr: frequentOne_set){
				  
				  for(int i=0;i<arr.length;i++){
					  writer.write(getKeyFromValue(arr[i])+" ");
					  //System.out.print(getKeyFromValue(arr[i])+" ");
				  }
				  List<Integer> arraylist = new ArrayList<>();
				  for(int i=0;i<arr.length;i++){
					  
					  arraylist.add(arr[i]);
				  }
				  writer.write("("+multiSet.get(arraylist).size()+")"+"\n");
				  //System.out.print("("+multiSet.get(arraylist).size()+")"+"\n");
			  }
			  
		  }
		 
		  
		 
		  
		  
		  
		    
			 
	  } //end of calculateFrequentItemSets
	   
	   public static void convert_set() throws FileNotFoundException
		{
			String file_name = FileName;
			File file = new File(file_name);
			int unique_int_val = 1;
			Scanner input = new Scanner(file);
			int line_no =1;
			while (input.hasNextLine()) 
			{
	            String next = input.nextLine();
	            
	            String[] item = next.split(" ");
	            int [] arr = new int[item.length] ;
	            List<Integer> storage = new ArrayList<Integer>();
	            for(int i=0;i<item.length;i++)
	            {
	            	if(hm.containsKey(item[i]))
	            	{	            		
	            		arr[i] = hm.get(item[i]);
	            		storage = One_set.get(arr[i]);
	            		storage.add(line_no);
	            		One_set.put(arr[i], storage);
	            	}
	            	else
	            	{
	            		
	            		hm.put(item[i], unique_int_val);
	            		arr[i]=unique_int_val;
	            		One_set.put(arr[i], new ArrayList<Integer>(Arrays.asList(line_no)));
	            		unique_int_val++;
	            		
	            	}
				    
				    
	            }
	            
	            line_no++;
			}
			
			number_of_transactions = line_no-1;
			input.close();
		}
	   public static void main(String [] args) throws IOException{
		
		   	  FileName = args[2]; 
		   	  String fname = args[3];
		   	  File f2 = new File(fname);
		   	  f2.createNewFile();
		      
		      // creates a FileWriter Object
		      FileWriter writer = new FileWriter(f2); 
		      
		      // Writes the content to the file
		      //writer.write("This\n is\n an\n example\n"); 
		      
			  minsup = Integer.parseInt(args[0]);
			  k = Integer.parseInt(args[1]);
			  convert_set();
			  	  
			  for(int k:One_set.keySet() ){
				  
				  if(One_set.get(k).size()>=minsup){
					  int[] arr = {k};
					  frequentOne_set.add(arr);
					  pre_level_frequent_set.add(arr);
				  }
			  }
			  
			  if(level>=k)
			    {
				  int l;
			    	 for(int [] array : frequentOne_set)
					    {
					    	for( l=0;l<array.length;l++)
					    	{
					    		
					    		writer.write(getKeyFromValue(array[l]).toString()+"\t");
					    		writer.write("("+One_set.get(array[l]).size()+")"+"\n");
					    		//System.out.print(getKeyFromValue(array[l]).toString()+"\t");
					    		//System.out.print("("+One_set.get(array[l]).size()+")"+"\n");
					    		
					    	}
					    
					    }
			    }
			  level=level+1;
			  while(frequentOne_set.size()>1)
			  {
			    if(frequentOne_set.size()!=0)
			    {
			      Candidate_gen(writer);
			      
			    }
			   
			    
			  }
			  
			  writer.flush();
		      writer.close();
			  
	}

}

