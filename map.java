

/*The Java code that is provided uses a graph data structure to construct a metro map system. 
The metro map is represented by the map class, where stations are nodes and connections between stations 
are edges. The code can be used to add and remove nodes, add edges, search for paths, and use Dijkstra's algorithm,
which determines the shortest path based on time or distance. The metro map and list of stations are visualised 
via the display_Map and display_Stations methods, respectively. In general, the code appears to provide a 
framework for metro network management and analysis.
*/

package TechSarathiInternship_MapNav;

import java.io.*;
import java.util.*;


public class map{

	/**
	 * Constructs a new instance of the Graph_M class and initializes the node map.
	 */
	public map(){
		vtcs_util = new HashMap<>();
	}


	/**
	* Represents a node in the graph with a map to store neighboring vertices and edge weights.
	*/
	public class node{
		HashMap<String, Integer> pseudo_hm_node = new HashMap<>();
	}

	// Static variable to store vertices in the graph
	static HashMap<String, node> vtcs_util;

	 //Removes a node from the graph along with its edges.
	public void node_remofin(String v){
		// Get the node to be removed
		node util = vtcs_util.get(v);
		
		// Get the list of neighboring vertices
		ArrayList<String> neigh_vtcs = new ArrayList<>(util.pseudo_hm_node.keySet());

		// Remove edges between the node to be removed and its neighbors
		for (String it : neigh_vtcs){

			node nbrVtx = vtcs_util.get(it);
			nbrVtx.pseudo_hm_node.remove(v);
		}

		// Remove the node from the graph
		vtcs_util.remove(v);
	}

	/**
	 * Checks if there is an edge between two vertices in the graph.
	 * @param v1 The name of the first node.
	 * @param v2 The name of the second node.
	 * @return True if there is an edge, false otherwise.
	 */
	public boolean edge_check(String v1, String v2){
		// Get the vertices corresponding to the given names
		node p1 = vtcs_util.get(v1);
		node p2 = vtcs_util.get(v2);
		
		// Check if both vertices exist and if there is an edge between them
		if (p1 == null || p2 == null || !p1.pseudo_hm_node.containsKey(v2)){
			return false;
		}

		return true;
	}

	/**
	 * Returns the number of edges in the graph.
	 * @return The number of edges.
	 */
	public int get_edges_num(){
		// Get the list of vertices in the graph
		ArrayList<String> vtcs_list = new ArrayList<>(vtcs_util.keySet());
		
		// cnt the total number of edges
		int cnt = 0;
		for (String it : vtcs_list){

			node util = vtcs_util.get(it);
			cnt = cnt + util.pseudo_hm_node.size();
		}

		// Since each edge is cnted twice, divide by 2 to get the actual number of edges
		return cnt/2;
	}

	

	/**
	 * Returns the number of vertices in the graph.
	 * @return The number of vertices.
	 */
	public int vrtc_size_get(){
		return this.vtcs_util.size();
	}

	/**
	 * Adds a new node to the graph with the given name.
	 * @param v The name of the new node.
	 */
	public void node_adddition(String v){
		// Create a new node instance
		node util = new node();
		
		// Add the node to the graph
		vtcs_util.put(v,util);
	}

	/**
	 * Performs dij_algo's algorithm to find the shortest path between source and dtination stations.
	 * @param s Source station name.
	 * @param d dtination station name.
	 * @param flg Flag to indicate whether non-air-conditioned travel is considered.
	 * @return The minimum cost (time or distance) to travel from source to dtination.
	 */

	public int dij_algo(String s, String d, boolean flg){
		
		// Create a Min Heap to prioritize vertices based on their costs
        Heap<dij_algoPair> heap = new Heap<>();

		// Initialize variables to store the result and the list of visited vertices
		int fin = 0;
		ArrayList<String> list = new ArrayList<>();

		// Create a HashMap to store dij_algoPair objects for each node
		HashMap<String, dij_algoPair> map = new HashMap<>();


		// Iterate through all vertices in the graph
		for (String it : vtcs_util.keySet()){
			// Create a dij_algoPair for the node and initialize its cost to infinity
			dij_algoPair psdo = new dij_algoPair();
			
			psdo.cost = Integer.MAX_VALUE;

			psdo.vname = it;

			// If the node is the source, update its cost to 0 and set the path so far
			if (it.equals(s)){
				psdo.cost = 0;
				psdo.psf = it;
			}

			// Add the dij_algoPair to the heap and map
			heap.add(psdo);
			map.put(it, psdo);
		}

		// Perform dij_algo's algorithm until the heap is not empty
		while (!heap.empty_check()){
			// Remove the pair with the minimum cost from the heap
			dij_algoPair p_util = heap.removal();

			// If the removed node is the destination, update the result and break the loop
			if (p_util.vname.equals(d)){
				fin = p_util.cost;
				break;
			}

			// Remove the node from the map
			map.remove(p_util.vname);

			// Add the node to the list of visited vertices
			list.add(p_util.vname);

			// Get the node object corresponding to the removed pair
			node v = vtcs_util.get(p_util.vname);

			// Iterate through the neighbors of the current node
			for (String it : v.pseudo_hm_node.keySet()){
				// Check if the neighbor is still in the map (unvisited)
				if (map.containsKey(it)){
					// Get the current cost and node object
					int oc = map.get(it).cost;
					int nc;
					node k = vtcs_util.get(p_util.vname);
					

					// Calculate the new cost based on whether non-air-conditioned travel is considered
					if (!flg) nc = p_util.cost + k.pseudo_hm_node.get(it);
					else nc = p_util.cost + 120 + 40 * k.pseudo_hm_node.get(it);

					// If the new cost is less than the old cost, update the dij_algoPair and heap
					if (nc < oc){
						dij_algoPair gp = map.get(it);
						gp.psf = p_util.psf + it;
						gp.cost = nc;

						heap.comingness(gp);
					}
				}
			}
		}
		return fin;
	}



	/**
	 * Checks if the graph contains a node with the given name.
	 * @param v The name of the node to check for.
	 * @return True if the node is present, false otherwise.
	 */
	public boolean checkNodeOrNot(String v){
		return map.vtcs_util.containsKey(v);
	}


	/**
	 * Removes an undirected edge between two vertices in the graph.
	 * @param v1 The name of the first node.
	 * @param v2 The name of the second node.
	 */
	public void removeEdge(String v1, String v2){
		// Get the vertices corresponding to the given names
		node p1 = vtcs_util.get(v1);
		node p2 = vtcs_util.get(v2);
		
		// Check if both vertices exist and if there is an edge between them
		if (p1 == null || p2 == null || !p1.pseudo_hm_node.containsKey(v2)) return;
		

		// Remove the edge between the vertices in both directions
		p1.pseudo_hm_node.remove(v2);
		p2.pseudo_hm_node.remove(v1);
	}

	/**
	 * Adds an undirected edge between two vertices in the graph with a specified finue.
	 * @param v1 The name of the first node.
	 * @param v2 The name of the second node.
	 */
	public void addEdge(String v1,String v2,int fin){
		// Get the vertices corresponding to the given names
		node p1 = vtcs_util.get(v1);
		node p2 = vtcs_util.get(v2);

		// Check if both vertices exist and if there is no existing edge between them
		if (p1 == null || p2 == null || p1.pseudo_hm_node.containsKey(v2)) return;
		

		// Add the edge between the vertices in both directions
		p1.pseudo_hm_node.put(v2, fin);
		p2.pseudo_hm_node.put(v1, fin);
	}

	/**
	 * Displays the Delhi Metro map, showing vertices and their neighboring vertices along with edge finues.
	 */
	public void display_Map(){
		System.out.println("Delhi Metro Map");
		
		
		// Get the list of vertices in the graph
		ArrayList<String> graph_nod = new ArrayList<>(vtcs_util.keySet());

		// Iterate through each node and its neighbors to display the map
		for (String it : graph_nod){
			String str_util = it + " =>\n";
			node v = vtcs_util.get(it);
			ArrayList<String> vtxpseudo_hm_node = new ArrayList<>(v.pseudo_hm_node.keySet());
			
			// Iterate through neighboring vertices and display them 
			for (String nbr_it : vtxpseudo_hm_node){

				str_util = str_util + "\t" + nbr_it + "\t";
				if (nbr_it.length() < 16) str_util = str_util + "\t";
				if (nbr_it.length() < 8) str_util = str_util + "\t";
				str_util = str_util + v.pseudo_hm_node.get(nbr_it) + "\n";
			}
			System.out.println(str_util);
		}
		
	}

		/**
	 * Checks if there is a path between two vertices in the graph.
	 * @param v1 The name of the starting node.
	 * @param v2 The name of the dtination node.
	 * @param vtcs_done A HashMap to keep track of vtcs_done vertices to avoid cycles.
	 * @return True if there is a path, false otherwise.
	 */
	public boolean checkPathOrNot(String v1, String v2, HashMap<String, Boolean> vtcs_done) {
		// Check if there is a direct edge between v1 and v2
		if (edge_check(v1, v2)) return true;
		

		// Mark the current node as vtcs_done to avoid revisiting in the same path
		vtcs_done.put(v1, true);

		// Get the node corresponding to v1
		node v = vtcs_util.get(v1);
		ArrayList<String> pseudo_hm_node = new ArrayList<>(v.pseudo_hm_node.keySet());

		// Traverse the neighbors of the current node
		for (String it : pseudo_hm_node){
			// Check if the neighbor has not been vtcs_done to avoid cycles
			if (!vtcs_done.containsKey(it)){
				// Recursively check for a path from the neighbor to the dtination node
				if (checkPathOrNot(it, v2, vtcs_done)) return true;
			}
		}

		// If no path is found in the current traversal, return false
		return false;
	}

	/**
	 * Displays the list of stations in the graph.
	 */
	public void display_Stations(){
		// Get the list of vertices in the graph
		ArrayList<String> graph_nod = new ArrayList<>(vtcs_util.keySet());

		int i = 1;
		
		// Iterate through each node and display its name as a station
		for (String it : graph_nod){
			System.out.println(i + ". " + it);
			i++;

		}
		
	}
		
	private class dij_algoPair implements Comparable<dij_algoPair>{
		String vname;
		String psf;
		int cost;

		@Override
		public int compareTo(dij_algoPair o) {
			return o.cost - this.cost;
		}
	}

		
	private class Pair {
		int min_dis;
		int min_time;
		String psf;
		String vname;
	}

	

	/**
	 * Extracts and identifies interchange stations from a formatted string.
	 * @return An ArrayList containing interchange information.
	 */
	public ArrayList<String> inter_fetch(String s){

		// System.out.println(s);
		// Initialize a freq variable to keep track of the number of interchanges
		int freq = 0;

		// Initialize an ArrayList to store interchange information
		ArrayList<String> inter_info = new ArrayList<>();
		
		// Split the string into an array using double space as a delimiter
		String ans[] = s.split("  ");
		
		// Add the first element (initial station) to the ArrayList
		inter_info.add(ans[0]);
		
		

		// Iterate through the array starting from the second element
		for(int i = 1; i < ans.length - 1; i++){
			// Find the index of the tilde (~) character in the current element
			int index = ans[i].indexOf('~');
			
			// Extract the line code from the current element
			String st = ans[i].substring(index + 1);

			
			if(st.length()!=2){
				// If the line code has a length other than 2, add the current element to the ArrayList
				inter_info.add(ans[i]);
			}
			// Check if the line code has a length of 2 (indicating a station)
			else{
				// Extract line cod of the previous and next elements
				String pre = ans[i - 1].substring(ans[i - 1].indexOf('~') + 1);
				String nxt = ans[i + 1].substring(ans[i + 1].indexOf('~') + 1);

				// Check if the previous and next line cod are the same
				if(pre.equals(nxt)){
					// If they are the same, add the current element to the ArrayList
					inter_info.add(ans[i]);
				} 
				else{
					// If they are different, add a formatted interchange information to the ArrayList
					inter_info.add(ans[i] + " -> " + ans[i + 1]);
					
					// Increment the interchange cnt
					freq++;

					// Increment the loop cnter to skip the next element (already considered)
					i++;
					
					
				}
			} 
			
		}
		
		// Add the interchange cnt to the ArrayList
		inter_info.add(Integer.toString(freq));
		
		
		inter_info.add(ans[ans.length - 1]);
		
		// Return the ArrayList containing interchange information
		return inter_info;
	}
		
		/**
	 * Finds the minimum distance path between source and dtination stations in the metro network.
	 * @param s Source station name.
	 * @param d dtination station name.
	 * @return A string containing the minimum distance path and the total distance.
	 */
	public String get_path_min_dist(String s, String d){
		// Initialize variables to track minimum distance and the corresponding path
		int min = Integer.MAX_VALUE;
		String pth = "";

		// Create a LinkedList to act as a stack for depth-first search
		LinkedList<Pair> stck = new LinkedList<>();

		// Create a HashMap to keep track of vtcs_done vertices
		HashMap<String, Boolean> vtcs_done = new HashMap<>();

		// Create a new pair for the source station and initialize it
		Pair sp = new Pair();
		sp.vname = s;
		sp.psf = s + "  ";
		sp.min_time = 0;
		sp.min_dis = 0;
		

		// Add the source pair to the stck
		stck.addFirst(sp);

		// Perform depth-first search until the stck is empty
		while (!stck.isEmpty()){
			// Remove a pair from the stck
			Pair p_util = stck.removeFirst();

			// Check if the node is already vtcs_done, if yes, skip
			if (vtcs_done.containsKey(p_util.vname)) continue;
			

			// Mark the node as vtcs_done
			vtcs_done.put(p_util.vname, true);

			// Check if the removed pair corresponds to the destination node
			if (p_util.vname.equals(d)){
				int tmp = p_util.min_dis;
				if (tmp < min){
					pth = p_util.psf;
					min = tmp;
				}
				continue;
			}

			// Get the node object corresponding to the removed pair
			node rm_pair_obj = vtcs_util.get(p_util.vname);

			// Get the neighbors of the current node
			ArrayList<String> pseudo_hm_node = new ArrayList<>(rm_pair_obj.pseudo_hm_node.keySet());

			// Iterate through the neighbors
			for (String it : pseudo_hm_node){
				// Process only unvtcs_done neighbors
				if (!vtcs_done.containsKey(it)){
					// Create a new pair for the neighbor and add it to the stack
					Pair psdo = new Pair();
					psdo.vname = it;
					psdo.psf = p_util.psf + it + "  ";
					psdo.min_dis = p_util.min_dis + rm_pair_obj.pseudo_hm_node.get(it);
					
					stck.addFirst(psdo);
				}
			}
		}
		pth=pth+Integer.toString(min);
		return pth;
	}

		
	public static void make_map_graph(map mp){
		// Adding metro stations to the graph
		mp.node_adddition("A~Y");
		mp.node_adddition("B~B");
		mp.node_adddition("C~Y");
		mp.node_adddition("D 21~B");
		mp.node_adddition("H~Y");
		mp.node_adddition("I~O");
		mp.node_adddition("J~BO");
		mp.node_adddition("M~B");
		mp.node_adddition("N 62~B");
		mp.node_adddition("P~P");
		mp.node_adddition("R~BY");
		mp.node_adddition("S~Y");
		mp.node_adddition("V~B");
		mp.node_adddition("Y~B");
		mp.node_adddition("Vi~Y");
		mp.node_adddition("Ne~YO");
		mp.node_adddition("Sh~O");
		mp.node_adddition("DD~O");
		mp.node_adddition("Ra~BP");
		mp.node_adddition("Net~PR");
		
		
	
		// Adding edges between metro stations with travel distance
		mp.addEdge("Ne~YO", "Sh~O", 2);
		mp.addEdge("Sh~O", "DD~O", 7);
		mp.addEdge("N 62~B", "B~B", 8);
		mp.addEdge("M~B", "J~BO", 7);
		mp.addEdge("P~P", "Ra~BP", 2);
		mp.addEdge("P~P", "Net~PR", 3);
		mp.addEdge("S~Y", "A~Y", 6);
		mp.addEdge("J~BO", "D 21~B", 6);
		mp.addEdge("H~Y", "S~Y", 15);
		mp.addEdge("C~Y", "Vi~Y", 5);
		mp.addEdge("A~Y", "R~BY", 7);
		mp.addEdge("B~B", "Y~B", 10);
		mp.addEdge("Y~B", "V~B", 8);
		mp.addEdge("DD~O", "I~O", 8);
		mp.addEdge("M~B", "Ra~BP", 2);
		mp.addEdge("Y~B", "R~BY", 6);
		mp.addEdge("R~BY", "M~B", 9);
		mp.addEdge("R~BY", "Ne~YO", 1);
		mp.addEdge("Ne~YO", "C~Y", 2);
	}
		
		
	public static String[] disp_sttn_codes(){
		// Display the list of stations along with their cod
		System.out.println("List of station along with its code:\n");
	
		// Get the keys (station names) from the graph
		ArrayList<String> utili = new ArrayList<>(vtcs_util.keySet());
	
		// Initialize variables for loop control and string manipulation
		int i = 1;
		int j = 0;
		int m = 1;

		StringTokenizer st_tnknizer;
		String tempo_str = "";
		
		// Array to store station code
		String cod_stns[] = new String[utili.size()];
	
		// Variable to store individual characters during string processing
		char c;
	
		// Iterate through each station name
		for (String it : utili){
			// Tokenize the station name
			j=0;
			st_tnknizer = new StringTokenizer(it);
			cod_stns[i - 1] = "";
			
	
			// Process each token in the station name
			while (st_tnknizer.hasMoreTokens()){
				tempo_str = st_tnknizer.nextToken();
				c = tempo_str.charAt(0);
	
				// Extract numerical part of the token
				while (c > 47 && c < 58){
					cod_stns[i - 1] += c;
					j++;
					c = tempo_str.charAt(j);
				}
	
				// Add non-numerical characters to the station code
				if ((c < 48 || c > 57) && c < 123) cod_stns[i - 1] += c;
			}
	
			// Handle cases where the station code is less than 2 characters
			if (cod_stns[i - 1].length() < 2) cod_stns[i - 1]=cod_stns[i - 1]+Character.toUpperCase(tempo_str.charAt(1));
	
			// Display station information in a formatted manner
			System.out.print(i + ". " + it + "\t");
			if (it.length() < (22 - m)) System.out.print("\t");
			if (it.length() < (14 - m)) System.out.print("\t");
			if (it.length() < (6 - m)) System.out.print("\t");
			System.out.println(cod_stns[i - 1]);
	
			i++;
	
			// Update formatting variable for station code alignment
			if (i == (int) Math.pow(10, m)) m++;
		}
	
		// Return the array containing station cod
		return cod_stns;
	}
	
	
	public static void main(String[] args) throws IOException{
		System.out.println("WELCOME TO MAP NAVIGATION SYSTEM. HERE YOU CAN DO ALL THESE.");
		// Create an instance of the Graph_M class to represent the metro map
		map grph = new map();

		// Populate the metro map with stations and connections
		make_map_graph(grph);
		
		
		// BufferedReader to read 
		BufferedReader ipsdo = new BufferedReader(new InputStreamReader(System.in));
		
		// Infinite loop to display the menu and handle user choices
		while(true){
			
			// Display menu options
			
			System.out.println("1. GET SHORTEST DISTANCE PATH TO REACH FROM SOURCE TO DESTINATION");  
			System.out.println("2. GET SHORTEST DISTANCE FROM SOURCE TO DESTINATION"); 
			System.out.println("3. SHOW THE MAP");  
			System.out.println("4. LIST ALL THE STATIONS IN THE MAP"); 
			System.out.println("5. EXIT THE MENU");

			System.out.println("\nEnter your choice. ");
			
			int choice = Integer.parseInt(ipsdo.readLine());
			
			if(choice == 5) System.exit(0);
			
			switch(choice){
				
				case 1:
					// Get shortest path (distance-wise) between source and destination stations
					
					System.out.println("Enter source and destination: ");
					String s1 = ipsdo.readLine();
					String s2 = ipsdo.readLine();
				
					HashMap<String, Boolean> vtcs_done2 = new HashMap<>();

					if(!grph.checkNodeOrNot(s1) || !grph.checkNodeOrNot(s2) || !grph.checkPathOrNot(s1, s2, vtcs_done2)) System.out.println("Input is invalid");
					
					else{
						ArrayList<String> str = grph.inter_fetch(grph.get_path_min_dist(s1, s2));

						int siz = str.size();

						System.out.println("SOURCE : " + s1);
						System.out.println("DESTINATION : " + s2);
						System.out.println("DISTANCE : " + str.get(siz-1)+"KM");
						System.out.println("NUMBER OF INTERCHANGES : " + str.get(siz-2));
						
						
						System.out.println("START  ->  " + str.get(0));
						for(int i=1; i<siz-3; i++) System.out.println(str.get(i));
						
						System.out.println(str.get(siz-3) + "   ->	END");
						
					}
					break;
				
				
				case 2:
					// Get shortest distance between source and dtination stations
					
					// Print the result
					ArrayList<String> utility = new ArrayList<>(vtcs_util.keySet());
					String codis[] = disp_sttn_codes();
					System.out.println("\n1. Enter serial number of stations\n2. Enter code of stations\n3. Enter name of stations\n");
					System.out.println("Enter your choice: ");

					int nx = Integer.parseInt(ipsdo.readLine());

					int j;
						
					String st1 = "";
					String st2 = "";
					System.out.println("Enter source and destination: ");

					if (nx == 3){
						st1 = ipsdo.readLine();
						st2 = ipsdo.readLine();
					}
					else if (nx == 2){
						String u1;
						String u2;
						u1= (ipsdo.readLine()).toUpperCase();

						for (j=0;j<utility.size();j++)
						if (u1.equals(codis[j])) break;
						u1 = utility.get(j);

						u2 = (ipsdo.readLine()).toUpperCase();
						for (j=0;j<utility.size();j++)
						if (u2.equals(codis[j])) break;

						u2 = utility.get(j);
					}
					else if (nx == 1){

						st1 = utility.get(Integer.parseInt(ipsdo.readLine())-1);
						st2 = utility.get(Integer.parseInt(ipsdo.readLine())-1);
					}
					
					
					else{

						System.out.println("Cannot process your choice");
						System.exit(0);
					}
				
					HashMap<String, Boolean> vtcs_done = new HashMap<>();

					if(!grph.checkNodeOrNot(st1) || !grph.checkNodeOrNot(st2) || !grph.checkPathOrNot(st1, st2, vtcs_done)) System.out.println("Input is invalid.");
					else System.out.println("SHORTEST DISTANCE FROM "+st1+" TO "+st2+" IS "+grph.dij_algo(st1, st2, false)+"km\n");
					break;
				
				case 3:
					// display full map
					grph.display_Map();
					break;

				case 4:
					// display all stations
					grph.display_Stations();
					break;
			
			}
		}
		
	}	
}
