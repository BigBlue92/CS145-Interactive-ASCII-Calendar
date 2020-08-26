//Ryan Mackenzie
//CS140 B - Assignment 1: Calendar Part 1
//Purpose: To create a functional calendar according to the parameters of assignment 1.
//RyansCalendar.java Revision 1.3
//1.1: Changed the borders from only "v" to "v^" and reformatted code. Properly named a few variables. Added a ">" to the ends of the borders for aesthetics.
//1.2: Created new methods digitAdjuster and dayFiller to make method drawRow smaller and less complicated
//1.3: Added mod statements into asciiArt, drawMonth, and drawRow to allow SIZE to be an odd integer. Before this SIZE had to be >= 6 && SIZE % 2 == 0.
//1.4: Big update to the calendar for assignment 2. Completely changed the structure of main to allow the program to run as an interactive calendar with a menu, and built several new methods for formatting.
//1.5: Even bigger update to the format of the calendar. I changed drawRow to draw one day at a time instead of an entire row of days. Added the week header, and got all of the menu working.

import java.util.*; //uses scanner and calendar

public class RyansCalendar2
{
   public static final int SIZE = 6; //controls SIZE of calendar. SIZE >= 4 (looks best at 6 and above, although technically 4 and 5 work fine too).
      
   public static void main(String[] args)
   {
   
   System.out.println("Welcome to my calendar program!");
   String userAnswer = "";
   int currentMonth = 0;
   
   do {
      userAnswer = userOptionsInterface(currentMonth);
   
      if (userAnswer.equalsIgnoreCase("e")) {
         currentMonth = customDate();
      } else if (userAnswer.equalsIgnoreCase("t")) {
         currentMonth = todaysDate();
      } else if (userAnswer.equalsIgnoreCase("n")) {
         currentMonth = nextMonth(currentMonth);
      } else if (userAnswer.equalsIgnoreCase("p")) {
         currentMonth = lastMonth(currentMonth);
      }
   } while (!userAnswer.equalsIgnoreCase("q"));
   
   }//end main method
   
   
   
   public static String userOptionsInterface(int currentMonth) {
      Scanner menuInput = new Scanner(System.in);
      String userInput = "";
      
      //The if else here makes it so the user can't select "next" or "previous" month if they just started the program and haven't selected a date yet. This is less confusing for the user than throwing an exception.
      if (currentMonth == 0) {
         System.out.println("Please type a command:\n\t\"e\" to enter a date and display the corresponding calendar\n\t\"t\" to get today's date and display this month's calendar");
         System.out.println("\t\"q\" to quit the program");     
      } else {
         System.out.println("Please type a command:\n\t\"e\" to enter a date and display the corresponding calendar\n\t\"t\" to get today's date and display this month's calendar\n\t\"n\" to display the next month");
         System.out.println("\t\"p\" to display the previous month\n\t\"q\" to quit the program");
      }
      
      userInput = menuInput.next();
      
      return userInput;
   }//end userOptionsInterface
   
   
   
   public static int customDate() {
      Scanner userDate = new Scanner(System.in);
      System.out.print("\nWhat date would you like to look at? (mm/dd)");
      String date = userDate.next();
      int monthOnly = Integer.parseInt(monthFromDate(date));
      int dayOnly = Integer.parseInt(dayFromDate(date));
      
      drawMonth(monthOnly, dayOnly);
      return monthOnly;
   }//end customDate
   
   
   
   public static int todaysDate() {
      Calendar currentDate = Calendar.getInstance();     
      int monthOnly = currentDate.get(Calendar.MONTH) + 1;
      int dayOnly = currentDate.get(Calendar.DATE); 
       
      drawMonth(monthOnly, dayOnly);
      return monthOnly;
   }//end todaysDate
   
   
   
      public static int nextMonth(int month) {
      if (month < 12) {
         month++;
         drawMonth(month, 0);
      } else {
         drawMonth(1, 0);
      }
      return month;
   }//end nextMonth
      
      
      
   public static int lastMonth(int month) {
      if (month > 1) {
         month--;
         drawMonth(month, 0);
      } else {
         drawMonth(12, 0);
      }
      return month;
   }//end lastMonth     
   
   
   
   
   
   //Pre: string input "mm" or "m", "dd" or "d"
   //Post: A month calendar that contains the input date.
   public static void drawMonth(int monthOnly, int dayOnly) {
      for(int headingSpace = 0; headingSpace <= SIZE * 3 + 1; headingSpace++) {
         System.out.print(" ");
      }//E0nd headingSpace
      System.out.println(monthOnly);//month header
      asciiArt();//month artwork, see full description below  
      weekHeader();
      
      int dayCount = 1;      
      
      if (monthOnly == 3 || monthOnly == 6) {
         for (int rowAmount = 0; rowAmount <= 5; rowAmount++) {
            dayCount = drawRow(rowAmount, monthOnly, dayOnly, dayCount);
         }
         for (int border = 1; border <= SIZE * 7 / 2; border++) {
            System.out.print("v^");
         }
      } else {
         for (int rowAmount = 0; rowAmount <= 4; rowAmount++) {
            dayCount = drawRow(rowAmount, monthOnly, dayOnly, dayCount);
         }
         for (int border = 1; border <= SIZE * 7 / 2; border++) {
            System.out.print("v^");
         }
      }
      
      if (SIZE % 2 == 1) {
         System.out.print("v");
      }
      System.out.print(">");
      
      displayDate(monthOnly, dayOnly);//Displays the date input for each month below the month.
             
   }//end drawMonth
   
   
   
   public static void weekHeader() {
      for (int week = 1; week < SIZE / 2; week++) {
         System.out.print(" ");
      }
      System.out.print("Sun");
      for (int week = 1; week < SIZE - 2; week++) {
         System.out.print(" ");
      }
      System.out.print("Mon");
      for (int week = 1; week < SIZE - 2; week++) {
         System.out.print(" ");
      }
      System.out.print("Tue");
      for (int week = 1; week < SIZE - 2; week++) {
         System.out.print(" ");
      }
      System.out.print("Wed");
      for (int week = 1; week < SIZE - 2; week++) {
         System.out.print(" ");
      }
      System.out.print("Thu");
      for (int week = 1; week < SIZE - 2; week++) {
         System.out.print(" ");
      }
      System.out.print("Fri");
      for (int week = 1; week < SIZE - 2; week++) {
         System.out.print(" ");
      }
      System.out.println("Sat");
   }//end weekHeader
   
   
   
   //Pre: rowNum = the week number of the month.
   //Post: Draws each week based on SIZE and adds 7 to dayNum each time it moves down a week.
   public static int drawRow(int rowNum, int month, int day, int dayCount) {
      
      for (int rowHeight = 0; rowHeight <= (SIZE / 2); rowHeight++) {
         if (rowHeight == 0) {
            for (int dividerWidth = 1; dividerWidth <= SIZE * 7 / 2; dividerWidth++) {
               System.out.print("v^");              
            }
            if (SIZE % 2 == 1) {
                System.out.print("v");
            }
            System.out.println(">");
                    
         } else if (rowHeight == 1) {  
            dayCount = dateLine(rowNum, month, day, dayCount);
            
         } else {
            dayFiller(7);
            System.out.println(">");          
         }    
      }
      return dayCount;   
   }//end method drawRow
   
   
   
   //Pre: string input = "mm/dd"
   //Post: retunrs  string "mm" or "m" if the first integer is 0.
   public static String monthFromDate(String monthOnly) {
      if (monthOnly.startsWith("1")) {  
         monthOnly = monthOnly.substring(0, 2);        
      } else {
         monthOnly = monthOnly.substring(1, 2);
      }
      
      return monthOnly;
   }//end month from date
   
   
   
   //Pre: string input = "mm/dd"
   //post: returns string "dd" or "d" if the first integer is 0.
   public static String dayFromDate(String fullDate) {
      String dayOnly = fullDate.substring(3);//pulls string from index 3 onward.
      
      if (dayOnly.startsWith("0")) {  
         dayOnly = dayOnly.substring(1);        
      } else {
         dayOnly = dayOnly.substring(0);
      }
      return dayOnly;
   }//end method dayFromDate 
   
   
   
   public static int dateLine(int rowNum, int month, int day, int dayCount) { 
      int dayTotal = daysInMonth(month);
      int lastWeekSpacing = lastWeekSpacer(month);
      
      if (rowNum == 0) {
         int weekStart = 0;
         weekStart = week1Spacer(month);
         dayFiller(weekStart);
         
         for (int dayNumberLine = 1; dayNumberLine <= 7 - weekStart; dayNumberLine++) {
            System.out.print("> " + (dayCount));
            digitAdjuster(day, dayCount);
            dayCount++;
         }
         
      } else {
         for (int dayNumberLine = 1; dayNumberLine <= 7; dayNumberLine++) {
            if (dayCount <= dayTotal) {
               System.out.print("> " + (dayCount));
               digitAdjuster(day, dayCount);
               dayCount++;
            }
         }
      }
      if (dayCount > dayTotal) {
         dayFiller(lastWeekSpacing);
      }
      System.out.println(">");
      return dayCount;             
   }//end dateLine



   public static int daysInMonth(int month) {
      if (month == 1) {
         return 31;
      } else if (month == 2) {
         return 28;
      } else if (month == 3) {
         return 31;
      } else if (month == 4) {
         return 30;
      } else if (month == 5) {
         return 31;
      } else if (month == 6) {
         return 30;
      } else if (month == 7) {
         return 31;
      } else if (month == 8) {
         return 31;
      } else if (month == 9) {
         return 30;
      } else if (month == 10) {
         return 31;
      } else if (month == 11) {
         return 30;
      }
      return 31;
   }//end daysInMonth   
   
 
   
   public static int week1Spacer(int month) {
      if (month == 1) {
         return 2;
      } else if (month == 2) {
         return 5;
      } else if (month == 3) {
         return 5;
      } else if (month == 4) {
         return 1;
      } else if (month == 5) {
         return 3;
      } else if (month == 6) {
         return 6;
      } else if (month == 7) {
         return 1;
      } else if (month == 8) {
         return 4;
      } else if (month == 9) {
         return 0;
      } else if (month == 10) {
         return 2;
      } else if (month == 11) {
         return 5;
      }
      return 0;
   }//end week1Spacer
   
   
   
   public static int lastWeekSpacer(int month) {
      if (month == 1) {
         return 2;
      } else if (month == 2) {
         return 2;
      } else if (month == 3) {
         return 6;
      } else if (month == 4) {
         return 4;
      } else if (month == 5) {
         return 1;
      } else if (month == 6) {
         return 6;
      } else if (month == 7) {
         return 3;
      } else if (month == 8) {
         return 0;
      } else if (month == 9) {
         return 5;
      } else if (month == 10) {
         return 2;
      } else if (month == 11) {
         return 0;
      }
      return 4;
   }//end lastWeekSpacer
   
   

   //Pre: a day number
   //Post: two spaces are printed after the day number on double digit days, and three for single digit days. Checks the day to see if was the input day and adds a star to it if the test passes.
   public static void digitAdjuster(int day, int dayCount) { 
      if (dayCount < 10) {
         if (dayCount == day) {
         
            System.out.print("*");
            
            for (int selectedDay = 1; selectedDay <= SIZE - 4; selectedDay++) {
               System.out.print(" ");         
            }          
         } else {
            for (int oneDigit = 1; oneDigit <= SIZE - 3; oneDigit++) {
            
               System.out.print(" ");
            }           
         }
      } else {
         if (dayCount == day) {
         
            System.out.print("*");
            
            for (int twoDigits = 1; twoDigits < SIZE - 4; twoDigits++) {
            
               System.out.print(" ");
            }
         } else {
            for (int twoDigits = 1; twoDigits < SIZE - 3; twoDigits++) {
            
               System.out.print(" ");
            }            
         }
      }
   }///end digitAdjuster
   
 
   
   //Pre:
   //Post:      
   public static void dayFiller(int cellAmount) {
      for (int cellFill = 1; cellFill <= cellAmount; cellFill++) {
         System.out.print(">");
      
         for (int daysFilled = 1; daysFilled < SIZE; daysFilled++) {           
            System.out.print(" ");
         }
      }           
   }//end dayFiller
   
   
      //Pre: mm, dd
   //Post: prints "Month: mm Day: dd" beneath each month
   public static void displayDate(int month, int day)
   {
      System.out.println();
      System.out.println("Month: " + month);
      System.out.println("Day: " + day);
      System.out.println();
      
   }//end method displayDate


   
    //My calendar header art. I specifically designed this art to line up with each day, and also designed it to scale properly with the whole calendar.
   public static void asciiArt() {
      for (int height = 1; height <= SIZE / 2; height++) {
         for (int alignToDays = 1; alignToDays <= 7; alignToDays++) {
            for(int traingle = 0; traingle <= (SIZE / 2) - height; traingle++) {
               System.out.print("/");
            }
            
            for(int traingle2 = SIZE / 2 - height; traingle2 < SIZE / 2 - 1; traingle2++) {
               System.out.print("\\");
            } 
                   
            for(int traingle3 = SIZE / 2 - height; traingle3 < SIZE / 2; traingle3++) {
               System.out.print("\\");
            }
            
            //fixes format for odd integer input on SIZE.
            if (SIZE % 2 == 1) {
            System.out.print("\\");
            }
            
            for(int traingle4 = 1; traingle4 <= (SIZE / 2) - height; traingle4++) {
               System.out.print("/");              
            }
         }
         System.out.println("/");
      }     
   }//end method asciiArt
   
   
     
}//end class RyansCalendar