#include <iostream>
#include <fstream>
#include <vector>
#include "rapidxml.hpp"
#include <string>
#include <math.h>
#include <algorithm>
#include <bits/stdc++.h>
#include <unordered_map>

using namespace std;
using namespace rapidxml;
//global variables for easy access throughout code
vector<vector<double>> main_graph;
map<pair<double,double>,double> cost;
unordered_map<string, double> imp_hashmap;
//declaring various useful structures for future use
struct node{

    string name="NULL";
    string id;
    string user;
    long double lat;
    long double lon;
    double distance=INT_MAX;
};
struct way{

    string user="NULL";
    string name="NULL";
    vector<string> nd_id;
};
struct calculate_distance{

    string id="null";
    double distance=INT_MAX;
};
struct path{

    double way_distance=0;
    vector<int> node_id;
};
//conversion to Radians
long double toRadians(const double degree)
{
    long double one_deg = (M_PI) / 180;
    return (one_deg * degree);
}
//Calculate crow fly distance between 2 coordinates
long double distance(long double lat1, long double long1,long double lat2, long double long2)
{

    lat1 = toRadians(lat1);
    long1 = toRadians(long1);
    lat2 = toRadians(lat2);
    long2 = toRadians(long2);

    // Haversine Formula
    long double dlong = long2 - long1;
    long double dlat = lat2 - lat1;

    long double ans = pow(sin(dlat / 2), 2) +
                          cos(lat1) * cos(lat2) *
                          pow(sin(dlong / 2), 2);

    ans = 2 * asin(sqrt(ans));

    double R = 6371;

    ans = ans * R;

    return ans;
}
vector<path> node_path;
//Modified UCS to print path taken as well as shortest distance
vector<double> uniform_cost_search(vector<int> goal, int start)
{
    int j;
    
    vector<double> answer;
  
    priority_queue<pair<double, int> > queue;

    for (int i = 0; i < goal.size(); i++)
        answer.push_back(INT_MAX);
    //initialises starting values
    queue.push(make_pair(0, start));
    path starting;
    starting.node_id.push_back(start);
    starting.way_distance=0;
    node_path.push_back(starting);
    //creates visited map
    map<int, int> visited;
 
    int count = 0;
 
    while (queue.size() > 0) {
 
        pair<double, int> p = queue.top();
        
        queue.pop();
        
        p.first *= -1;
        //checks to see if goal has been reached 
        if (p.second==goal[0]) {
             
            int index = 0;
            if (answer[index] == INT_MAX)
                count++;
             
            if (answer[index] > p.first)
                answer[index] = p.first;
                        
            if (count == goal.size())
                return answer;
        }
        //find index of current shortest path
        for(j=0;j<node_path.size();j++)
            if(p.first==node_path[j].way_distance)
                break;

        //updates necessary distances and path for next iteration
        if (visited[p.second] == 0)
            for (int i = 0; i < main_graph[p.second].size(); i++) {
                
                queue.push(make_pair((p.first +
                  cost[make_pair(p.second, main_graph[p.second][i])]) * -1,
                  main_graph[p.second][i]));
    
                path temp;
                temp.node_id=node_path[j].node_id;
                temp.node_id.push_back(main_graph[p.second][i]);                
                temp.way_distance=(p.first +cost[make_pair(p.second, main_graph[p.second][i])]) ;                
                node_path.push_back(temp);
              
            }
        //erases pre-exisiting path 
        node_path.erase( node_path. begin() + j );
        
        visited[p.second] = 1;
    }
 
    return answer;
}
 

xml_document<> doc;
xml_node<> * root_node = NULL;

int main(void)
{
    
    vector<node> node_list;
    vector<way> way_list;
    vector<calculate_distance> distance_list;
    cout <<"\nParsing map data" << endl;
    string holder="name";
    string tempstr;
    string user_input_name;
    double counter,counter_nd=0;
    long long int i;
    double user_input_node,user_input;
    //loads the file map.osm into buffer and assign doc to index 0
    ifstream theFile ("map.osm");
    vector<char> buffer((istreambuf_iterator<char>(theFile)), istreambuf_iterator<char>());
    buffer.push_back('\0');

    doc.parse<0>(&buffer[0]);
    //assigns root node
    root_node = doc.first_node("osm");

    xml_node<> * ending_node = root_node->last_node("node");
    xml_node<> * ending_way = root_node->last_node("way");
    ending_way=ending_way->next_sibling();
    ending_node=ending_node->next_sibling();
    //for loop to traverse nodes and store necessary values in vector element
    for (xml_node<> * student_node = root_node->first_node("node");student_node->first_attribute("id")->value()!=ending_node->first_attribute("id")->value(); student_node = student_node->next_sibling())
    {

        node temp_node;
        temp_node.id=student_node->first_attribute("id")->value();
        temp_node.lat=std::stold(student_node->first_attribute("lat")->value());
        temp_node.lon=std::stold(student_node->first_attribute("lon")->value());
        temp_node.user=student_node->first_attribute("user")->value();
        for(xml_node<> * student_name_node = student_node->first_node("tag"); student_name_node; student_name_node = student_name_node->next_sibling())
        {
            if(holder==student_name_node->first_attribute("k")->value()){
                temp_node.name=student_name_node->first_attribute("v")->value();
                break;
            }
        }
        node_list.push_back(temp_node);
        calculate_distance holder_temp;
        distance_list.push_back(holder_temp);
        imp_hashmap[temp_node.id]=node_list.size()-1;
    }
  
    
    main_graph.resize(node_list.size());
    //for loop to traverse way and create cost and graph data structures
    for (xml_node<> * student_node = root_node->first_node("way");student_node->first_attribute("id")->value()!=ending_way->first_attribute("id")->value(); student_node = student_node->next_sibling())
    {
        way temp_way;
        temp_way.user=student_node->first_attribute("user")->value();
        xml_node<> * ending_nd = student_node->last_node("nd");
        xml_node<> * student_name_node;
        for(student_name_node = student_node->first_node("nd");student_name_node->first_attribute("ref")->value()!=ending_nd->first_attribute("ref")->value();student_name_node = student_name_node->next_sibling()){
            if(temp_way.nd_id.size()>0){
                int n=temp_way.nd_id.size();
                //creating graph and updating cost of perticular edge
                main_graph[imp_hashmap[temp_way.nd_id[n-1]]].push_back(imp_hashmap[student_name_node->first_attribute("ref")->value()]);
                main_graph[imp_hashmap[student_name_node->first_attribute("ref")->value()]].push_back(imp_hashmap[temp_way.nd_id[n-1]]);
                double temp=distance((node_list[imp_hashmap[temp_way.nd_id[n-1]]].lat),(node_list[imp_hashmap[temp_way.nd_id[n-1]]].lon),(node_list[imp_hashmap[student_name_node->first_attribute("ref")->value()]].lat),(node_list[imp_hashmap[student_name_node->first_attribute("ref")->value()]].lon));
                cost[make_pair(imp_hashmap[temp_way.nd_id[n-1]],imp_hashmap[student_name_node->first_attribute("ref")->value()])]=temp;
                cost[make_pair(imp_hashmap[student_name_node->first_attribute("ref")->value()],imp_hashmap[temp_way.nd_id[n-1]])]=temp;
               
              
            }
            
            temp_way.nd_id.push_back(student_name_node->first_attribute("ref")->value());               
        }
        if(temp_way.nd_id.size()>0){
            int n=temp_way.nd_id.size();
            //creating graph and updating cost of perticular edge
            main_graph[imp_hashmap[temp_way.nd_id[n-1]]].push_back(imp_hashmap[student_name_node->first_attribute("ref")->value()]);
            main_graph[imp_hashmap[student_name_node->first_attribute("ref")->value()]].push_back(imp_hashmap[temp_way.nd_id[n-1]]);
            double temp=distance((node_list[imp_hashmap[temp_way.nd_id[n-1]]].lat),(node_list[imp_hashmap[temp_way.nd_id[n-1]]].lon),(node_list[imp_hashmap[student_name_node->first_attribute("ref")->value()]].lat),(node_list[imp_hashmap[student_name_node->first_attribute("ref")->value()]].lon));       
            cost[make_pair(imp_hashmap[temp_way.nd_id[n-1]],imp_hashmap[student_name_node->first_attribute("ref")->value()])]=temp;
            cost[make_pair(imp_hashmap[student_name_node->first_attribute("ref")->value()],imp_hashmap[temp_way.nd_id[n-1]])]=temp;
            
        }   
        temp_way.nd_id.push_back(student_name_node->first_attribute("ref")->value());
        //adds name of way if present to way_list element
        for(xml_node<> * student_name_node = student_node->first_node("tag"); student_name_node; student_name_node = student_name_node->next_sibling())
        {
            if(holder==student_name_node->first_attribute("k")->value()){
                temp_way.name=student_name_node->first_attribute("v")->value();
                break;
            }
        }
        way_list.push_back(temp_way);
        
    }
    vector<int> goal;
    vector<double> check;
    string user_input_for_shortest_distance1,user_input_for_shortest_distance2;

    int user_selection_input;
    //while loop to print user interface
    while(true){
        cout<<"\n\nPlease Enter the Key of the operation you want to perform:\n"<<endl;

        cout<<"Press 1 to Search for an element\n"<<endl;
        cout<<"Press 2 to Find K Closest Nodes from an Element\n"<<endl;
        cout<<"Press 3 to find the Minimum distance between 2 Nodes\n"<<endl;
        cout<<"Press -1 to Exit\n"<<endl;
        cin>>user_selection_input;
        switch(user_selection_input){
            case 1:

                cout<<"Please enter the name of the node u seek"<<endl;
                    cin>>user_input_name;
                cout<<"\n";
                
                //traverse nodes and prints details if substring is found
                for(int i=0;i<node_list.size();i++){

                    if (node_list[i].name.find(user_input_name) != string::npos) {
                            cout<<node_list[i].name<<":"<<endl;
                            cout<<"ID: "<<node_list[i].id<<endl;
                            cout<<"Latitude: "<<node_list[i].lat<<endl;
                            cout<<"Longitiute: "<<node_list[i].lon<<endl;
                            cout<<"\n";
                    } 
                }
                //traverse way and prints details if substring is found
                for(int i=0;i<way_list.size();i++){

                    if(way_list[i].name.find(user_input_name)!=string::npos){
                        cout<<way_list[i].name<<": "<<endl;
                        cout<<"User ID: "<<way_list[i].user<<endl;
                        cout<<"\n";
                    }
                }
                
                break;
            case 2:

                cout<<"\nPlease enter the ID of the node whose closest neighbor you seek"<<endl;
                cin>>user_input_node;
                for(i=0;i<node_list.size();i++){
                    if(std::stod(node_list[i].id)==user_input_node){
                        break;
                    }
                }
                if(i==node_list.size()){
                    cout<<"\nInvalid Node ID\n"<<endl;
                    break;
                }
                    
                double x_axis,y_axis;
                x_axis=(node_list[i].lat);
                y_axis=(node_list[i].lon);
                //calcuates distance for all nodes and sorts
                for(i=0;i<node_list.size();i++){
                    node_list[i].distance=distance(x_axis,y_axis,(node_list[i].lat),(node_list[i].lon));
                    distance_list[i].distance=node_list[i].distance;
                    distance_list[i].id=node_list[i].id;
                }
                sort(distance_list.begin(), distance_list.end(), [](calculate_distance a, calculate_distance b){
                return a.distance < b.distance;
                });
                cout<<"Please enter the number of the closest neighbors you seek"<<endl;
                cin>>user_input;
                cout<<"\n    ID:          Distance:\n"<<endl;
                //prints first k indexes
                for(int i=1;i<=user_input;i++)
                cout<<i<<"   "<<distance_list[i].id<<"   "<<distance_list[i].distance<<"\n"<<endl;
                break;
            case 3:   
                cout<<"\nPlease Enter the node you want to start from"<<endl;
                cin>>user_input_for_shortest_distance1;
                for(i=0;i<node_list.size();i++){
                    if(node_list[i].id==user_input_for_shortest_distance1){
                        break;
                    }
                }
                if(i==node_list.size()){
                    cout<<"\nInvalid Node ID\n"<<endl;
                    break;
                }
                cout<<"\nPlease Enter the node you want to end at from"<<endl;
                cin>>user_input_for_shortest_distance2;
                for(i=0;i<node_list.size();i++){
                    if(node_list[i].id==user_input_for_shortest_distance2){
                        break;
                    }
                }
                if(i==node_list.size()){
                    cout<<"\nInvalid Node ID\n"<<endl;
                    break;
                }
                //adds to goal and calls UCS function
                goal.push_back(imp_hashmap[user_input_for_shortest_distance2]);


                check=uniform_cost_search(goal,imp_hashmap[user_input_for_shortest_distance1]);
                //if INT_MAX is output no path exists 
                if(check[0]==INT_MAX){
                    cout<<"\nNo Possible Path"<<endl;
                    break;
                }
                else
                    cout<<"\nDistance: "<<check[0]<<" Km"<<endl;
                //to print path
                cout<<"\npress 1 to print path or -1 to exit\n"<<endl;
                cin>>user_selection_input;
                cout<<"\n";
                if(user_selection_input==1){
                for(int i=0;i<node_path.size();i++){
                    if(node_path[i].way_distance==check[0]){
                        for(int j=0;j<node_path[i].node_id.size();j++){
                            cout<<node_list[node_path[i].node_id[j]].id<<endl;
                        }
                    }
                }
                }
                node_path.clear();
                check.pop_back();
                goal.pop_back();
                break;
            //standard cases
            case -1:
                return 0;
          
            default :
                cout<<"\ninvalid Input!!!\n"<<endl;
            
        }
       
    }

    return 0;
}
