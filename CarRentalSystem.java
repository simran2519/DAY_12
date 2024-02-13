package day12_oops;

import javax.print.attribute.standard.ColorSupported;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Vehicle     //(ABSTRACTION)
{
   private String make;
   private String model;
   private String year;
   int rentalPrice;
   //Constructor to initialize the values
   public Vehicle(String make,String model,String year,int rentalPrice)
   {
       this.make=make;
       this.model=model;
       this.year=year;
       this.rentalPrice=rentalPrice;
   }
   //Getters to get the values
    public String getMake()
    {
        return make;
    }
    public String getModel()
    {
        return model;
    }
    public String getYear()
    {
        return year;
    }
    public int getRentalPrice()
    {
        return rentalPrice;
    }

    //method to calculate Rental Cost
   abstract public int carRentalCost(int hours);
}

//Car class which is inherited from the Vehicle class(INHERITANCE)
class Car extends Vehicle
{
    private int numSeats;
    private String fuelType;
     Car(String make,String model,String year, int rentalPrice,int numSeats,String fuelType)
     {
         super(make,model,year,rentalPrice);   //inherited from base class
         this.fuelType=fuelType;
         this.numSeats=numSeats;
     }

    public int getNumSeats() {
        return numSeats;
    }

    public String getFuelType() {
        return fuelType;
    }

    @Override
    public int getRentalPrice() {
        return super.getRentalPrice();
    }
    public String toString() //to print the car details
     {
         return "[Make="+getMake()+" Model= "+getModel() +" Year= "+getYear()+" Rent Price= "+getRentalPrice()+" Total Seats= "+getNumSeats()+" FuelType "+ getFuelType()+ " ]";
     }

    @Override
    public int carRentalCost(int hours)
    {
        return rentalPrice*hours;
    }
}
class Customer
{
    private String name;
    private String email;
    public ArrayList< Car> rentedVehicles= new ArrayList<>();   //To store the rented Cars of the Customer

    public Customer(String name,String email) //constructor
    {
        this.name=name;
        this.email=email;
    }
    //getters to get the details of Customer
    public String getEmail()
    {
        return email;
    }
    public String getName() {
        return name;
    }

    public String toString()   //to print object of Customer Class
    {
        return "[Name="+getName()+", Email= "+getEmail()+"]";
    }
}
class RentalAgency {
    Scanner sc = new Scanner(System.in);
    List<Car> carList;              //To store all the Cars
    List<Integer> status = new ArrayList<>();   //To keep the status if the car is available for rent or not
    List<Customer> customerList=new ArrayList<>();  //To store all the Customer

    RentalAgency() {
        carList = new ArrayList<>();
    }
//    ----------------------------------------------------------------------
    public void addCar(Car newcar) {                                  //1. Add Car
        carList.add(newcar);
        status.add(0);                        //0 means the car is not given anyone for rent yet
        System.out.println("Car is added Successfully");
    }

    public void addCustomer(Customer newCustomer) {                  //2. Add Customer
        customerList.add(newCustomer);
        System.out.println("Customer is added Successfully");
    }

    public void renting()                                          //3. Renting
    {
        System.out.println("Enter Customer's email id");
        String email=sc.next();
        System.out.println("Select the car you want to get as rent");
        for (Car car : carList) {
            System.out.println(car);
        }
        System.out.println("Enter the Make of Car that you have selected");
        String make = sc.next();
        Car cartoRent = null;
        int i = 0;
        for (Car car : carList) {               //Find the car and checking its status which is selected by customer
            if (car.getMake().equals(make) && status.get(i) == 0) {
                cartoRent = car;
                status.add(i, 1);
                break;
            }
            else
            {
                System.out.println("Car is not available");
            }
            i++;
        }
        if(cartoRent!=null)              //if The car is availabe , then checking if the customer exists in the list or otherwise we need to add the customer firslty
        {
            System.out.println("You have selected:--------- ");
            System.out.println(cartoRent);
            Customer customertoAdd= null;
            for(Customer customer: customerList)
            {
                if(email.equals(customer.getEmail()))
                {
                    customertoAdd=customer;
                    break;
                }
                else
                {
                    System.out.println("Customer does not exists");
                }
            }
            customertoAdd.rentedVehicles.add(cartoRent);    //If the customer is present, then add it in the list where all rented cars of customer should be placed
            System.out.println("The car has been given you for rent");
        }

    }

    public void returning()                  //4. Method for returning the vehicle by customer and then telling customer their payable amount
    {
        System.out.println("Enter Customer's email id");
        String email=sc.next();
        System.out.println("Enter the make of Car Customer wants to Return");
        String make=sc.next();
        System.out.println("Enter the no. of hours the customer has taken the car for rent");
        int hours=sc.nextInt();
        Customer customertoReturn=null;
        Car cartoReturn=null;
        for(Car car: carList)          //Matching whether the car exists or not
        {
            if(car.getMake().equals(make))
            {
                cartoReturn=car;
                break;
            }
            else
            {
                System.out.println("This car is not present");
            }
        }
        int i=0;
        for(Customer customer : customerList)      //Then checking  the customer which want to return car for rent
        {
            if(customer.getEmail().equals(email))
            {
                customertoReturn=customer;
                break;
            }
            else
            {
                System.out.println("Your email is not correct");
            }
            i++;
        }
        if(customertoReturn.rentedVehicles.contains(cartoReturn))   //cheking if the customer has been taken the car for rent or not
        {
           int total= cartoReturn.carRentalCost(hours);
            System.out.println("Car is returned and Customer's Payable Amount is: " + total);
            status.add(i,0);
            customertoReturn.rentedVehicles.remove(cartoReturn);          //if the customer has taken the car then calculate the car's
        }
        else
        {
            System.out.println("You have already Returned the Car");
        }
    }
    public int totalRent()            //to find the total Rent of the customer
    {
        System.out.println("Enter Customer's email");
        String email =sc.next();
        System.out.println("Enter the no. of hours that Customer had taken car as rent");
        int hours=sc.nextInt();
        Customer customertoCal=null;
        for(Customer customer: customerList)
        {
            if(customer.getEmail().equals(email))
            {
                customertoCal=customer;
                break;
            }
        }
        int total=0;
        for(Car car: customertoCal.rentedVehicles)
        {
            System.out.println(car +" " + car.getRentalPrice());
            total=car.carRentalCost(hours);     //Here I am supposing that for each car the Customer has taken same time for rent That is why there no. of hours are also same
        }
        return total;
    }

    //Display All Cars
    public void displayCars()
    {
        System.out.println("All Cars are: ");
        for(Car car :carList)
        {
            System.out.println(car);
        }
    }
    public void displayCustomers()
    {
        System.out.println("All Customers are");
        for(Customer customer : customerList)
        {
            System.out.println(customer);
        }
    }
}
    public class CarRentalSystem {
        static Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {
            RentalAgency rt = new RentalAgency();
            System.out.println("Welcome");
            while (true) {
                System.out.println("Select which operation you want to perform");
                System.out.println("0. Exit");
                System.out.println("1. Add Car");
                System.out.println("2. Add Customer");
                System.out.println("3. Renting of Vehicle");
                System.out.println("4. Return Vehicle");
                System.out.println("5. Calculate Total Rent(Payable Amount)");
                System.out.println("6. Show Car Details");
                System.out.println("7. Show Customer Details");
                int n = sc.nextInt();
                if (n == 0) {
                    System.out.println("Exiting");
                    break;
                }
                switch (n) {
                    case 1:
                    {
                        Car newcar = createCar();
                        rt.addCar(newcar);
                        break;
                    }
                    case 2:
                    {
                        Customer newcustomer=addCus();
                        rt.addCustomer(newcustomer);
                        break;
                    }
                    case 3:
                    {
                        rt.renting();
                        break;
                    }
                    case 4:
                    {
                        rt.returning();
                        break;
                    }
                    case 5:
                    {
                        int total=rt.totalRent();
                        System.out.println("The total Payable Amount of Customer is :"+ total);
                        break;
                    }
                    case 6:
                    {
                        rt.displayCars();
                        break;
                    }
                    case 7:
                    {
                        rt.displayCustomers();
                        break;
                    }
                    default:
                    {
                        System.out.println("You have entered an invalid option");
                        break;
                    }
                }
            }
        }

        public static Car createCar() {
            System.out.println("Enter the make of the car");
            String make = sc.next();
            System.out.println("Enter the model of the car");
            String model = sc.next();
            System.out.println("Enter the year in which the car has been made");
            String year = sc.next();
            System.out.println("Enter the rental Price");
            int rentalPrice = sc.nextInt();
            System.out.println("Enter the number of seats in car");
            int numSeats = sc.nextInt();
            System.out.println("Enter the Type of fuel in car");
            String fuelType = sc.next();
            Car newcar = new Car(make, model, year, rentalPrice, numSeats, fuelType);
            return newcar;
        }

        public static Customer addCus()
        {
            System.out.println("Enter the name of Customer");
            String name=sc.nextLine();
            sc.nextLine();
            System.out.println("Enter the email Id of Customer");
            String email= sc.next();
            Customer newcus= new Customer(name,email);
            return newcus;
        }
    }
