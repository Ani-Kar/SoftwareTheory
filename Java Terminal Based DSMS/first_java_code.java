import java.util.ArrayList;
import java.util.Scanner;
/*
Assumptions made:
1)All the entries are of the correct data-type as specified in instruction text (might create error if data-types are different)
2)Any error or cancellation of any entity during creation,edit,or deletion will cancel said process and revert all changes

Basic Functionalities:-
1)Add,Delete,Print all types of entities and the information they hold
2)Add Products to Manufacturer
3)Add copies of products in shops
4)Add and Process an Order with necessary conditions
6)Assigns Delivery Agent as per specifications 
5)List inventory and purchase history of customer

Additional Functionalities:-
1)Holds more than 1 manufacturer for each product incase of high demand of product
2)Ability to edit products and manufacturers by adding to the information they hold
3)Ability to edit the zip code of delivery agents
4)Checks to make sure that no ID is repeated and prints appropriate error messages for it(Point 2 in Assumptions for more detail)
5)Added Custom Tuple class as import of tuple was not working
*/

//Custom Tuple Class with necessary constructors
class Tuple  {
  int num_available_products;
  String name_product;
  int product_index;
  Tuple(){
    this.name_product="name";
    this.num_available_products=0;
    this.product_index=0;
  }
  Tuple(int temp_num,String temp_name,int temp_index){
    this.num_available_products= temp_num;
    this.name_product=temp_name;
    this.product_index=temp_index;   
  }
}
//First Class which holds all functions of Manufacturers and Products
class Medicine_Store_products extends Tuple {
    int result;
    int manufacturer_num;
    int product_num;
    Scanner sc= new Scanner(System.in);
    Medicine_Store_customers obj;
  //Manfucaturer Class with necessary constructors and print method
  class manufacturer  {
    int id;
    String name;
    ArrayList<String> products = new ArrayList<String>();
    ArrayList<Integer> products_id = new ArrayList<Integer>();
    manufacturer(){
      this.id= 0;
      this.name="name";
      this.products = new ArrayList<String>();
      this.products_id= new ArrayList<Integer>();
    }
    manufacturer(int temp_id,String temp_name, ArrayList<String> temp_products,ArrayList<Integer> temp_products_id){
      this.id=temp_id;
      this.name=temp_name;
      this.products = new ArrayList<String>(temp_products);
      this.products_id = new ArrayList<Integer>(temp_products_id);
    }
    manufacturer(int temp_id,String temp_name, String temp_manufacturers,int temp_manu_id){
      this.id=temp_id;
      this.name=temp_name;
      this.products = new ArrayList<String>();
      this.products.add(temp_manufacturers);
      this.products_id = new ArrayList<Integer>();
      this.products_id.add(temp_manu_id);
    
    }
    
    void print(ArrayList<String> temp_products,ArrayList<Integer> temp_products_id){
      System.out.println();
      String str1 = String.format("PRODUCT NAME"); 
      String str2 = String.format("PRODUCT ID"); 
      System.out.format("%-20s",str1);System.out.format("%-20s",str2);System.out.println();
      for (int i = 0; i < temp_products.size(); i++){
        System.out.format("%-20s",temp_products.get(i)); System.out.format("%-20d",temp_products_id.get(i) ); System.out.println("\n");
      }
      if(temp_products.size()==0){
        System.out.println("\nERROR: There are no products!!!\n");
      }
    }
  }
  //product Class with necessary constructors and print method
  class product{
    int id;
    String name;
    ArrayList<String> manufacturers = new ArrayList<String>();
    ArrayList<Integer> manuf_id = new ArrayList<Integer>();
    product(){
      this.id= 0;
      this.name="name";
      this.manufacturers = new ArrayList<String>();
      this.manuf_id=new ArrayList<Integer>();
    }
    product(int temp_id,String temp_name, ArrayList<String> temp_manufacturers,ArrayList<Integer> temp_manuf_id){
      this.id=temp_id;
      this.name=temp_name;
      this.manufacturers = new ArrayList<String>(temp_manufacturers);
      this.manuf_id=new ArrayList<Integer>(temp_manuf_id);
    }
    product(int temp_id,String temp_name, String temp_manufacturers,Integer temp_manuf_id){
      this.id=temp_id;
      this.name=temp_name;
      this.manufacturers = new ArrayList<String>();
      this.manufacturers.add(temp_manufacturers);
      this.manuf_id=new ArrayList<Integer>();
      this.manuf_id.add(temp_manuf_id);
    }
    void print(ArrayList<String> temp_manufacturers,ArrayList<Integer> temp_manufacturers_id){
      System.out.println();
      String str1 = String.format("MANUFACTURER NAME"); 
      String str2 = String.format("MANUFACTURER ID"); 
      System.out.format("%-20s",str1);System.out.format("%-20s",str2);System.out.println();
      for (int i = 0; i < temp_manufacturers.size(); i++){
        System.out.format("%-20s",temp_manufacturers.get(i)); System.out.format("%-20d",temp_manufacturers_id.get(i) ); System.out.println("\n");
      }
      if(temp_manufacturers.size()==0){
        System.out.println("\nERROR: there are no manufacturers!!!\n");
      }
    }    
  }  
  ArrayList<manufacturer> product_manufacturer = new ArrayList<manufacturer>(50);
  ArrayList<product> product_arr = new ArrayList<product>(50);
  //Impt method to which calls necessary function based on user input
  void assign(int user_function_num1,int user_function_num2,Medicine_Store_customers imp_temp){
    this.obj=imp_temp;
    if(user_function_num1==1){
      switch(user_function_num2) {
        case 1:
          add_manufacturer();
          break;
        case 2:
          print_manufacturer(product_manufacturer);
          break;
        case 3:
          delete_manufacturer();
          break;
        case 4:
          edit_manu();
          break;
        default:
          return;
      }   
    }
    else{
      switch(user_function_num2) {
        case 1:
          add_product();
          break;
        case 2:
          print_product(product_arr);       
          break;
        case 3:
          delete_product();
          break;
        case 4:
          edit_prod();
          break;
        default:
          return;
      }
    }               
  }
  //add Manufacturer to Manufacturer Arraylist
  void add_manufacturer(){
    System.out.println("\nPlease follow the following steps to add a Manufacturer: -\n");

    System.out.println("Please enter the 4 digit ID of Manufacturer to add-");
    int id_temp=Integer.parseInt(sc.nextLine());

    System.out.println("\nPlease enter the name of Manufacturer to add-");
    String name_temp= sc.nextLine(); 

    manufacturer temp= new manufacturer();
    int id_product_temp;
    //check to see if same ID exists 
    for (manufacturer temp_manufacturer : product_manufacturer) {   
      if (temp_manufacturer.id==(id_temp)) {
        System.out.println("\nERROR: This manufacturer already exists!!!\n");
        return;
      }
    }
    
    temp.name=name_temp;
    temp.id=id_temp;
    
    while(!name_temp.equals("-1")){

      System.out.println("\nPlease enter the name of products produced by "+ temp.name+ " one at a time: -\n");
      System.out.println("NOTE: Press -1 to exit any time\n");
      name_temp= sc.nextLine();
      if(name_temp.equals("-1"))
        break;
      System.out.println("\nPlease enter the ID-\n");
      id_product_temp=Integer.parseInt(sc.nextLine());
      //check to see if same Product exists to Manufacturer
      for (Integer temp_check : temp.products_id) {
        if (id_product_temp==(temp_check)) {
          System.out.println("\nERROR: This product already exists!!!\n");
          return;
        }
      }
      temp.products.add(name_temp);
      temp.products_id.add(id_product_temp);
      edit_products(name_temp,id_product_temp,temp.name,temp.id,1);    
    }
    product_manufacturer.add(new manufacturer(id_temp,temp.name,temp.products,temp.products_id));
    System.out.println("\n"+temp.name+" has been successfully added!\n");
    //print_manufacturer(product_manufacturer);
  }
  //deletes manufacturer                    
  void delete_manufacturer(){
    System.out.println("\nPlease follow the following steps to delete a Manufacturer");

    print_manufacturer(product_manufacturer);

    System.out.println("Please select the ID of Manufacturer to delete or press -1 to cancel-\n");
    int delete_input=Integer.parseInt(sc.nextLine());
    if(delete_input==-1)
      return;
    //finds manufacturer to delete based on user input
    for (manufacturer temp_manufacturer : product_manufacturer){
        if (temp_manufacturer.id==(delete_input)) {
            for (int i = 0; i < temp_manufacturer.products.size(); i++)
              edit_products(temp_manufacturer.products.get(i), temp_manufacturer.products_id.get(i), temp_manufacturer.name,temp_manufacturer.id, -1);
            
            product_manufacturer.remove(temp_manufacturer);
            System.out.println(temp_manufacturer.name+" has been successfully removed!\n");
            break;
        }
    }

  }
  //Edit Manufacturer method
  void edit_manu(){
    String name_temp="";
    int edit_temp_product;
    System.out.println("\nPlease follow the following steps to edit a Manufacturer:-");
    print_manufacturer(product_manufacturer);

    System.out.println("Please select the ID of manufacturer to edit or press -1 to cancel-\n");
    int edit_input=Integer.parseInt(sc.nextLine());
    //check to see if user wants to terminate program
    if(edit_input==-1)
      return;
    for (manufacturer temp_manufacturer : product_manufacturer){
        if (temp_manufacturer.id==(edit_input)){
          while(!name_temp.equals("-1")){
            System.out.println("Please enter the name of the product manufacturered by "+ temp_manufacturer.name+ "to add to list\n");
            System.out.println("NOTE: Press -1 to exit any time\n");
            name_temp= sc.nextLine();
            System.out.println("Please enter the ID:\n");
            edit_temp_product=Integer.parseInt(sc.nextLine());
            //check to avoid repitition 
            for (Integer temp_check : temp_manufacturer.products_id) {
              if (edit_temp_product==(temp_check)) {
                System.out.println("\nERROR: This product already exists!!!\n");
                return;
              }
            }
            temp_manufacturer.products.add(name_temp);
            temp_manufacturer.products_id.add(edit_temp_product);
            edit_products(name_temp,edit_temp_product,temp_manufacturer.name,temp_manufacturer.id,1);
            System.out.println(temp_manufacturer.name+" has been successfully edited!\n");
            break;
          }
        }
    }
  }
  //Method to edit product list when adding manufacturers            
  void edit_products(String name_of_product,int id_of_product,String name_of_manufacturer,int id_of_manufacurer, int add_delete){  
    if(add_delete>0){
      for (product temp_product : product_arr) {     
        if (temp_product.id==id_of_product) {          
            temp_product.manufacturers.add(name_of_manufacturer);
            temp_product.manuf_id.add(id_of_manufacurer);
            return;
        }
      }    
      product_arr.add(new product(id_of_product,name_of_product,name_of_manufacturer,id_of_manufacurer));
      obj.add_to_product_list(name_of_product,id_of_product);
    }
    else{
      for (product temp_product : product_arr){      
        if (temp_product.id==id_of_product){
            temp_product.manufacturers.remove(name_of_manufacturer);
            int temp=temp_product.manuf_id.indexOf(id_of_manufacurer);
            temp_product.manuf_id.remove(temp);
            return;
        }
      }
    }  
  }
  void print_manufacturer(ArrayList<manufacturer> print_manuf){
    System.out.println("\n");
      String str1 = String.format("MANUFACTURER NAME"); 
      String str2 = String.format("MANUFACTURER ID"); 
      System.out.format("%-20s",str1);System.out.format("%-20s",str2);System.out.println();
      for (manufacturer temp_manufacturer : product_manufacturer){
        System.out.format("%-20s",temp_manufacturer.name); System.out.format("%-20d",temp_manufacturer.id ); System.out.println("\n");
      }
    System.out.println("Please enter the ID of Manufacturer whose detail you want to view, -1 to return.\n");
    int edit_input=Integer.parseInt(sc.nextLine());
    if(edit_input==-1)
      return;
    else{
      for (manufacturer temp_manufacturer : product_manufacturer) {
        if(temp_manufacturer.id==edit_input){
          temp_manufacturer.print(temp_manufacturer.products,temp_manufacturer.products_id);
        }
      }
    }
  }
  //Adds products
  void add_product(){
    System.out.println("Please follow the following steps to add a Product\n");

    System.out.println("Please enter the name of Product-");
    String name_temp= sc.nextLine();

    int manu_id_temp;
    System.out.println("\nPlease enter the 4 digit ID of Product to add-");
    int id_temp=Integer.parseInt(sc.nextLine());

    product temp= new product();
    //check to avoid adding same ID product
    for (product temp_product : product_arr) {
      if (temp_product.id==(id_temp)) {
        System.out.println("\nERROR: This product already exists!!!\n");
        return;
      }
    }
    temp.name=name_temp;
    temp.id=id_temp;
    while(!name_temp.equals("-1")){
      System.out.println("\nPlease enter the name of manufacturers of "+ temp.name+ " one at a time\n");
      System.out.println("NOTE: Press -1 to exit any time\n");
      name_temp= sc.nextLine();
      if(name_temp.equals("-1"))
        break;
      System.out.println("Please enter the ID-\n");
      manu_id_temp=Integer.parseInt(sc.nextLine());
      //check to avoid adding same ID manufacturer to product
      for (Integer temp_check : temp.manuf_id) {
        if (manu_id_temp==(temp_check)) {
          System.out.println("\nERROR: This manufacturer already exists!!!\n");
          return;
        }
      }
      temp.manufacturers.add(name_temp);
      temp.manuf_id.add(manu_id_temp);
      edit_manufacturers(temp.name,temp.id,name_temp,manu_id_temp, 1);   
    }
    product_arr.add(new product(temp.id,temp.name,temp.manufacturers,temp.manuf_id));
    System.out.println(temp.name+" has been successfully added!\n");
    obj.add_to_product_list(temp.name,temp.id);         
    //print_product(product_arr);
  }  
  //Delete method similar to delete manufacturer       
  void delete_product(){
    System.out.println("\nPlease follow the following steps to delete a Product");
    print_product(product_arr);
    System.out.println("Please select the id to delete or press -1 to cancel\n");
    int delete_input=Integer.parseInt(sc.nextLine());
    if(delete_input==-1)
      return;
    for (product temp_product : product_arr) {
        if (temp_product.id==(delete_input)) {
          for (int i = 0; i < temp_product.manufacturers.size(); i++)
              edit_manufacturers(temp_product.name, temp_product.id, temp_product.manufacturers.get(i), temp_product.manuf_id.get(i), -1);

            product_arr.remove(temp_product);
            System.out.println(temp_product.name+" has been successfully removed!\n");
            break;
        }          
    }
  } 
  //edit product similar to edit manufacturer       
  void edit_prod(){
    String name_temp="";
    int edit_temp_manuf;
    System.out.println("\nPlease follow the following steps to edit a Product");
    print_product(product_arr);
    System.out.println("\nPlease select the ID of product to edit or press -1 to cancel\n");
    int edit_input=Integer.parseInt(sc.nextLine());
    if(edit_input==-1)
      return;
    for (product temp_product : product_arr) {
        if (temp_product.id==(edit_input)) {
          while(!name_temp.equals("-1")){

            System.out.println("Please enter the name of manufacturers of: "+ temp_product.name+ "to add to list\n");
            System.out.println("NOTE:Press -1 to exit any time\n");
            name_temp= sc.nextLine();
            System.out.println("Please enter the ID:\n");
            edit_temp_manuf=Integer.parseInt(sc.nextLine());
            for (Integer temp_check : temp_product.manuf_id) {
              if (edit_temp_manuf==(temp_check)) {
                System.out.println("\nERROR: This product already exists!!!\n");
                return;
              }
            }
            temp_product.manufacturers.add(name_temp);
            temp_product.manuf_id.add(edit_temp_manuf);
            edit_manufacturers(temp_product.name , temp_product.id, name_temp, edit_temp_manuf, 1);
            System.out.println(temp_product.name+" has been successfully edited!\n");
            break;
          }
        }
    }

  }        
  void print_product(ArrayList<product> print_product){
    System.out.println("\n");
      String str1 = String.format("PRODUCT NAME"); 
      String str2 = String.format("PRODUCT ID"); 
      System.out.format("%-20s",str1);System.out.format("%-20s",str2);System.out.println();
      for (product temp_product : product_arr){
        System.out.format("%-20s",temp_product.name); System.out.format("%-20d",temp_product.id ); System.out.println("\n");
      }
    System.out.println("Please enter the ID of Product whose detail you want to view, -1 to return.\n");
    int edit_input=Integer.parseInt(sc.nextLine());
    if(edit_input==-1)
      return;
    else{
      for (product temp_product : product_arr) {
        if(temp_product.id==edit_input){
          temp_product.print(temp_product.manufacturers,temp_product.manuf_id);
        }
      }
    }
  }
  //edits manufacturers while creating products
  void edit_manufacturers(String name_of_product,int id_of_product,String name_of_manufacturer,int id_of_manuf, int add_delete){
    if(add_delete>0){
      for (manufacturer temp_manufacturer : product_manufacturer) {      
        if (temp_manufacturer.id==id_of_manuf) {
            temp_manufacturer.products.add(name_of_product);
            temp_manufacturer.products_id.add(id_of_product);
            return;
        }
      }    
      product_manufacturer.add(new manufacturer(id_of_manuf,name_of_manufacturer,name_of_product,id_of_product));
    }
    else{
      for (manufacturer temp_manufacturer : product_manufacturer) {      
        if (temp_manufacturer.id==id_of_manuf) {
            temp_manufacturer.products.remove(name_of_product);
            int temp=temp_manufacturer.products_id.indexOf(id_of_product);
            temp_manufacturer.products_id.remove(temp);
            return;
        }
      }
    }
  }
  //simple functions to join the 2 classes
  int get_num(){  
    return product_arr.size();
  }
  Tuple get_tuple(int i){
    Tuple temp= new Tuple();
    temp.name_product=product_arr.get(i).name;
    temp.product_index=product_arr.get(i).id;
    temp.num_available_products=0;
    return temp;
  }      
}
//Class which holds customers,shops,and delivery agents
class Medicine_Store_customers extends Tuple{
  Scanner sc= new Scanner(System.in);
  //required classes their constructors and required print methods
  class customer  {
    int id;
    String name;
    int zip_code;
  customer(){
    this.id=0;
    this.name="name";
    this.zip_code=0;            
  }
  customer(int temp_id,String temp_name,int temp_zip){
    this.id=temp_id;
    this.name=temp_name;
    this.zip_code=temp_zip;        
  }
}       
  
  class shop  {
    int id;
    String name;
    int zip_code;
    ArrayList<Tuple> inventory = new ArrayList<Tuple>();
    shop(){
      this.id=0;
      this.name="name";
      this.zip_code=0;
      this.inventory = new ArrayList<Tuple>();
    }
    shop(int temp_id,String temp_name,int temp_zip,ArrayList<Tuple> temp_inventory){

      this.id=temp_id;
      this.name=temp_name;
      this.zip_code=temp_zip;
      this.inventory=new ArrayList<Tuple>(temp_inventory);      
    }
    void print(ArrayList<Tuple>temp_index){
      System.out.println("\n");
      String str1 = String.format("PRODUCT NAME"); 
      String str2 = String.format("PRODUCT ID"); 
      String str3 = String.format("QUANTITY"); 
      System.out.format("%-20s",str1);System.out.format("%-20s",str2);System.out.format("%-20s",str3);System.out.println();
      for (int i = 0; i < temp_index.size(); i++){
        System.out.format("%-20s",temp_index.get(i).name_product); System.out.format("%-20d",temp_index.get(i).product_index ); System.out.format("%-20d",temp_index.get(i).num_available_products ); System.out.println("\n");
      }
          if(temp_index.size()==0){
            System.out.println("there are no products");
          }
    }
    void print_products(shop ordering_shop){      
      for(int i=0;i< ordering_shop.inventory.size();i++)
        System.out.println("ID: "+ordering_shop.inventory.get(i).product_index +"\n"+"Name: "+ordering_shop.inventory.get(i).name_product +"\n" +"Quantity: "+ordering_shop.inventory.get(i).num_available_products+"\n");
    }      
  }
  class delivery  {
    int id;
    String name;
    int zip_code;
    int products_delivered;
    delivery(){
      this.id=0;
      this.name="name";
      this.zip_code=0;
      this.products_delivered=0;
    }
    delivery(int temp_id,String temp_name,int temp_zip){
      this.id=temp_id;
      this.name=temp_name;
      this.zip_code=temp_zip;
      this.products_delivered=0;
    }      
  }

  ArrayList<customer> customer_list=new ArrayList<customer>();
  ArrayList<shop> shop_list=new ArrayList<shop>();
  ArrayList<shop> order_list=new ArrayList<shop>();
  ArrayList<delivery> delivery_list=new ArrayList<delivery>();
  //switch case to assign function based on user input
  void assign_customers(int user_function_num1,int user_function_num2,Medicine_Store_products imp_temp){
    if(user_function_num1==2){
      switch(user_function_num2) {
        case 1:
          add_customer();
          break;
        case 2:
          print_customers();
          break;
        case 3:
          delete_customers();
          break;
        case 4:
          add_order();
          break;
        case 5:
          purchase_history();
          break;
        default:
          return;
      }      
    }
    else if (user_function_num1==4)
    {
      switch(user_function_num2) {
        case 1:
          add_shops(imp_temp);
          break;
        case 2:
          print_shop();
          break;
        case 3:
          delete_shops();
          break;
        case 4:
          edit_shops();
          break;       
        default:
          return;
      }
    }
    else
    {
      switch(user_function_num2) {
        case 1:
          add_delivery();
          break;
        case 2:
          print_delivery();
          break;
        case 3:
          delete_delivery();
          break;
        case 4:
          edit_delivery();
          break;        
        default:
          return;
        }
    }
  }
  //prints purchase history
  void purchase_history(){
    print_customers();
    System.out.println("\nPlease enter the Customer ID: -");
    int id_temp=Integer.parseInt(sc.nextLine());
    int counter=1;
    for(shop temp_shop : order_list){
      if(temp_shop.id==id_temp){
        System.out.println(":\n"+counter+":\n");
        temp_shop.print_products(temp_shop);
        counter++;
      }
    }
  }
  //adds order to customer
  void add_order(){
    System.out.println("\nPlease follow the following steps to add an Order: -");
    print_customers();
    customer ordering_customer=new customer();
    shop ordering_shop=new shop();
    shop new_shop=new shop();
    System.out.println("\nPlease enter the Customer ID: -");
    int id_temp=Integer.parseInt(sc.nextLine());
    for (customer temp_customer : customer_list) {
      if (temp_customer.id==(id_temp)) {
          ordering_customer=temp_customer;
          break;
      }
    }
    print_shop();
    System.out.println("\nPlease enter the ID of Shop or Warehouse to order from:-");
    id_temp=Integer.parseInt(sc.nextLine());
    System.out.println("");
    for (shop temp_shop : shop_list) {
      if (temp_shop.id==(id_temp)) {
          if(temp_shop.zip_code!=ordering_customer.zip_code){
            System.out.println("\nERROR: Invalid Store\n");
            return;
          }
          ordering_shop=temp_shop;
          break;
        }
      }
    new_shop=order(ordering_customer,ordering_shop);
    new_shop.print_products(new_shop);
    if(new_shop.id==-1)
    return;
    System.out.println("\nPlease enter 1 to confirm order or -1 to cancel: -");
    id_temp=Integer.parseInt(sc.nextLine());
    if(id_temp==1){
      new_shop.id=ordering_customer.id;
      new_shop.name=ordering_customer.name;
      new_shop.zip_code=ordering_customer.zip_code;
      order_list.add(new_shop);
      assign_delivery_agent();
    }
    else{
      add_products_to_store(new_shop,ordering_shop);
    }    
  }
  //adds product back to store inventory incase of cancellation
  void add_products_to_store(shop new_shop,shop ordering_shop){
    for(Tuple temp_tuple : new_shop.inventory){
      for(Tuple temp_tuple2 : ordering_shop.inventory){
        if(temp_tuple.product_index==temp_tuple2.product_index){
          temp_tuple2.num_available_products=temp_tuple2.num_available_products+temp_tuple.num_available_products;
        }
      }
    }
  }
  //assigns agent
  void assign_delivery_agent(){
    int min=2147483647;
    int index=0;
    for (delivery temp_delivery : delivery_list) {
      if (temp_delivery.products_delivered<min) {
        index=temp_delivery.id;
        min=temp_delivery.products_delivered;          
        }
      }
      for (delivery temp_delivery : delivery_list) {
        if (temp_delivery.id==index) {
          System.out.println("\nDelivery Agent:-"+temp_delivery.name+" has been assigned! and Order has been placed successfully!!");
          temp_delivery.products_delivered++;
          return;
          }
        }
  }
  //adds required quanitities to customer cart and gets ready for processing
  shop order(customer ordering_customer, shop ordering_shop){
    int prod_id=0;
    int quantity;
    ordering_shop.print_products(ordering_shop);
    shop new_shop= new shop();
    
    while(prod_id!=-1){
      System.out.println("\nPlease enter the ID of the product you want to add to cart: -");
      System.out.println("\nNOTE: Press -1 to exit any time");
      prod_id=Integer.parseInt(sc.nextLine());
      if(prod_id==-1&&new_shop.inventory.size()==0){
        new_shop.id=-1;
        return new_shop;
      }
      if(prod_id==-1)   
        return new_shop;    
      System.out.println("\nPlease enter the quantity of the product you want to add to cart: -");
      quantity=Integer.parseInt(sc.nextLine());
      for(Tuple temp_tuple : ordering_shop.inventory){
        if((temp_tuple.product_index==prod_id)){
          if(temp_tuple.num_available_products>=quantity){
          Tuple adding_tuple=new Tuple(quantity, temp_tuple.name_product, temp_tuple.product_index);

          new_shop.inventory.add(adding_tuple);
          temp_tuple.num_available_products=temp_tuple.num_available_products-quantity;
          break;
          }
          else
          System.out.println("\nERROR: invalid quantity!!!\n");
        }        
      }
    }
    return new_shop;
  }
  //fucntion to edit shops to increase quantity of products
  void edit_shops(){
    System.out.println("\nPlease follow the following steps to edit a Shop or Warehouse: -");
    print_shop();
    int id_temp=0;
    int prod_id=0;
    int new_inv=0;    
      System.out.println("\nPlease enter the ID of the shop you want to change inventory of: -");
      id_temp=Integer.parseInt(sc.nextLine());
      for (shop temp_shop : shop_list) {
        if (temp_shop.id==(id_temp)) {
          while(prod_id!=-1){
            System.out.println("\nPlease enter the ID of the product you want to change inventory of: -");
            System.out.println("NOTE: Press -1 to exit any time");
            prod_id=Integer.parseInt(sc.nextLine());
            if(prod_id==-1)
              return;
            for(Tuple temp_tuple : temp_shop.inventory){
              if(temp_tuple.product_index==prod_id){
                System.out.println("\nPlease enter new Quantity of Product:-");
                new_inv=Integer.parseInt(sc.nextLine());
                temp_tuple.num_available_products=new_inv;
                break;
              }
            }
          }
          return;
        }     
    }
  }
  //simple function to update product list of shops
  void add_to_product_list(String name_of_product,int id_of_product){
    Tuple temp=new Tuple(0, name_of_product, id_of_product);
    int flag;
    for (shop temp_shop : shop_list) {
        flag=0;
        for(Tuple temp_tuple : temp_shop.inventory){
          if(temp_tuple.product_index==id_of_product){
            flag=1;
            break;
          }
        }
        if(flag==0){
          temp_shop.inventory.add(temp);
        }     
    }
  }
  //adds customer
  void add_customer(){
      System.out.println("\nPlease follow the following steps to add Customer");
      System.out.println("\nPlease enter the name of Customer: -");
        String name_temp= sc.nextLine();
        System.out.println("\nPlease enter the 4 digit ID of "+ name_temp+": -");
        int id_temp=Integer.parseInt(sc.nextLine());
        System.out.println("\nPlease enter the zip code of "+ name_temp+": -");
        int zip_temp=Integer.parseInt(sc.nextLine());
        customer temp= new customer();
        //checks for pre-existing customer
        for (customer temp_customer : customer_list) {
        if (temp_customer.id==(id_temp)) {
          System.out.println("\nERROR: This Customer already exists!!!\n");
            return;
          }
        }
        temp.name=name_temp;
        temp.id=id_temp;
        temp.zip_code=zip_temp;
            
        customer_list.add(new customer(temp.id,temp.name,temp.zip_code));
        System.out.println("\nCustomer successfully added!!!\n");
        //print_customers();
  }
  //simple printing function
  void print_customers(){
    System.out.println("\n");
      String str1 = String.format("CUSTOMER ID"); 
      String str2 = String.format("CUSTOMER NAME"); 
      String str3 = String.format("ZIP CODE"); 
      System.out.format("%-20s",str1);System.out.format("%-20s",str2);System.out.format("%-20s",str3);System.out.println();
      for (customer temp_customer : customer_list){
        System.out.format("%-20d",temp_customer.id); System.out.format("%-20s",temp_customer.name ); System.out.format("%-20d",temp_customer.zip_code ); System.out.println("\n");
      }
      return;
  }
  //delets customer
  void delete_customers(){
      System.out.println("\nPlease follow the following steps to delete Customer: -\n");
        print_customers();
            System.out.println("Please select the ID to delete or press -1 to cancel\n");
            int delete_input=Integer.parseInt(sc.nextLine());
            if(delete_input==-1)
              return;
            for (customer temp_customer : customer_list) {
                if (temp_customer.id==(delete_input)) {
                  customer_list.remove(temp_customer);
                  System.out.println("\nCustomer successfully deleted");
                    break;
                    }                  
            }
  }
  //adds delivery agent
  void add_delivery(){
    System.out.println("\nPlease follow the following steps to add a Delivery Agent: -");
    System.out.println("\nPlease enter the name of Delivery Agent to add: -");
        String name_temp= sc.nextLine();
        System.out.println("\nPlease enter the 4 digit id to add:-");
        int id_temp=Integer.parseInt(sc.nextLine());
        System.out.println("\nPlease enter the zip code to add:-");
        int zip_temp=Integer.parseInt(sc.nextLine());
        delivery temp= new delivery();
        for (delivery temp_delivery : delivery_list) {
        if (temp_delivery.id==(id_temp)) {
          System.out.println("\nERROR: This Agent already exists!!!\n");
            return;
          }
        }
        temp.name=name_temp;
        temp.id=id_temp;
        temp.zip_code=zip_temp;
            
        delivery_list.add(new delivery(temp.id,temp.name,temp.zip_code));
        System.out.println("\nDelivery Agent successfully added!!");
        //print_delivery();
  }
  //prints delivery agent
  void print_delivery(){
    System.out.println("\n");
      String str1 = String.format("DELIVERY AGENT ID"); 
      String str2 = String.format("DELIVERY AGENT NAME"); 
      String str3 = String.format("ZIP CODE"); 
      String str4 = String.format("DELIVERIES"); 
      System.out.format("%-20s",str1);System.out.format("%-20s",str2);System.out.format("%-20s",str3);System.out.format("%-20s",str4);System.out.println();
      for (delivery temp_delivery : delivery_list){
        System.out.format("%-20d",temp_delivery.id); System.out.format("%-20s",temp_delivery.name ); System.out.format("%-20d",temp_delivery.zip_code );  System.out.format("%-20d",temp_delivery.products_delivered );System.out.println("\n");
      }
    
  }
  //deltes delivery agent
  void delete_delivery(){
    System.out.println("\nPlease follow the following steps to delete a Delivery Agent: -\n");
      print_delivery();
            System.out.println("Please select the ID to delete or press -1 to cancel\n");
            int delete_input=Integer.parseInt(sc.nextLine());
            if(delete_input==-1)
              return;
            for (delivery temp_delivery : delivery_list) {
                if (temp_delivery.id==(delete_input)) {
                  delivery_list.remove(temp_delivery);
                  System.out.println("\nDelivery Agent successfully removed");
                    break;
                    }                    
            }
  }
  //edits zipcode of delivery agent
  void edit_delivery(){
      System.out.println("\nPlease follow the following steps to edit a Delivery Agent: -\n");
      print_delivery();
      System.out.println("Please select the ID to edit or press -1 to cancel\n");
            int delete_input=Integer.parseInt(sc.nextLine());
            if(delete_input==-1)
              return;
              System.out.println("Please Enter the new zip code: -\n");
              int edit_input=Integer.parseInt(sc.nextLine());
              for (delivery temp_delivery : delivery_list) {
                if (temp_delivery.id==(delete_input)) {
                  temp_delivery.zip_code=edit_input;
                  
                    break;
                    }
              }
  }
  //adds shops and updates inventory products to set starting quantity as 0
  void add_shops(Medicine_Store_products imp_temp){
    System.out.println("\nPlease follow the following steps to add a Shop or Warehouse: -");
    System.out.println("\nPlease enter the name of Shop or market to add: -");
        String name_temp= sc.nextLine();
        System.out.println("\nPlease enter the 4 digit ID to add:-");
        int id_temp=Integer.parseInt(sc.nextLine());
        System.out.println("\nPlease enter the zip code to add:-");
        int zip_temp=Integer.parseInt(sc.nextLine());
        shop temp= new shop();
        for (shop temp_shop : shop_list) {
        if (temp_shop.id==(id_temp)) {
          System.out.println("\nERROR: This Shop already exists!!\n");
            return;
          }
        }
        temp.name=name_temp;
        temp.id=id_temp;
        temp.zip_code=zip_temp;
        int count=imp_temp.get_num();
        for(int i=0;i<count;i++){

          temp.inventory.add(imp_temp.get_tuple(i));
        }
        shop_list.add(new shop(temp.id,temp.name,temp.zip_code,temp.inventory));
        System.out.println("\nShop or Warehouse successfully added\n");
        //print_shop();
  }
  //simple printing function
  void print_shop(){
    for (shop temp_shop : shop_list) {
      System.out.println("\nID: "+temp_shop.id);
      System.out.println("NAME: "+temp_shop.name);
      System.out.println("ZIP CODE: "+temp_shop.zip_code);
      temp_shop.print(temp_shop.inventory);
    }
  }
  //deletes shops
  void delete_shops(){
    System.out.println("\nPlease follow the following steps to delete a Shop or Warehouse: -\n");
    print_shop();
            System.out.println("\nPlease select the ID to delete or press -1 to cancel: -");
            int delete_input=Integer.parseInt(sc.nextLine());
            if(delete_input==-1)
              return;
            for (shop temp_shop : shop_list) {
                if (temp_shop.id==(delete_input)) {
                  shop_list.remove(temp_shop);
                  System.out.println("\nShop or Warehouse successfully deleted\n");
                    break;
                    }                    
            }
  }
}


class first_java_code {
    public static void main(String[] args) {
      //switch case to get what entity and function the user wants to perform
        Medicine_Store_products products_and_manufacturer= new Medicine_Store_products();
        Medicine_Store_customers customers_and_shops = new Medicine_Store_customers();
        Scanner sc = new Scanner(System.in);
        while(true){
        System.out.println("Welcome to Anikets Medicine Shop!");
        System.out.println("");
        System.out.println("Select an Entity:");
        System.out.println("");
        System.out.println("Press 1 to select Manufacturers.");
        System.out.println("");
        System.out.println("Press 2 to select Customers.");
        System.out.println("");
        System.out.println("Press 3 to select Products.");
        System.out.println("");
        System.out.println("Press 4 to select Shops and Warehouses.");
        System.out.println("");
        System.out.println("Press 5 to select Delivery Agents.");
        System.out.println("");
        System.out.println("Press -1 to Quit.");
        System.out.println("");

        int user_input1,user_input2;
        String holder="";
        user_input1=Integer.parseInt(sc.nextLine()); 
        switch(user_input1) {
          case 1:
            holder="Manufacturer";
            break;
          case 2:
            holder="Customer";
            break;
          case 3:
            holder="Product";
            break;
          case 4:
            holder="Shop or Warehouse";
            break;
          case 5:
            holder="Delivery Agent";
            break;
          case -1:
            break;

          default:
          System.out.println("Invalid input");
        }
        //System.out.print("\033[H\033[2J");

        if(user_input1==-1){
          sc.close();
          return;
        }
            

        System.out.println("");
        System.out.println("Select a function to perform:");
        System.out.println("");
        System.out.println("Press 1 to add a new "+ holder+".");
        System.out.println("");
        System.out.println("Press 2 to print entities of "+holder+".");
        System.out.println("");
        System.out.println("Press 3 to delete a "+holder+".");
        System.out.println("");
        if(user_input1==2)
        System.out.println("Press 4 to add an order.");
        else
        System.out.println("Press 4 to edit an existing entity.");
        System.out.println("");
        if(user_input1==2){
          System.out.println("Press 5 to show purchase history of an entity.");
          System.out.println("");
        }

        System.out.println("Press -1 to go back to the main menu.");
        System.out.println("");

        user_input2=Integer.parseInt(sc.nextLine()); 
        if(user_input2==-1)
            continue;
        //calls each method depending on user input
        switch(user_input1) {
            case 1:
              products_and_manufacturer.assign(user_input1, user_input2,customers_and_shops);
              break;
            case 2:
              customers_and_shops.assign_customers(user_input1, user_input2,products_and_manufacturer);
              break;
            case 3:
              products_and_manufacturer.assign(user_input1, user_input2,customers_and_shops);
              break;
            case 4:
              customers_and_shops.assign_customers(user_input1, user_input2,products_and_manufacturer);
              break;
            case 5:
              customers_and_shops.assign_customers(user_input1, user_input2,products_and_manufacturer);
              break;
            case -1:
              break;

            default:
            System.out.println("Invalid input");
          }        
        }
        
      }  
      
}
    
  

  

