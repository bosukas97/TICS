package Primary;

import java.util.LinkedList;

/**
 * TestTCS is the access point for Traffic Control System (TCS) interaction with the testbed.
 * There exist a total of 5 method calls for interacting with the testbed properly.
 * These 5 methods are detailed and demo'd below. Additional tips are included for
 * interacting with the testbed.
 *
 *   Methods of interest.
 *       Class: Lanes
 *           public boolean getCarOnLane()
 *           public boolean getEmergencyOnLane()
 *           public void setColor(SignalColor color)
 *
 *       Class: Lights
 *           public void setColor(SignalColor c)
 *           public boolean isPedestrianAt()
 *
 */
class TestTCS extends Thread {

    private int count = 0;

    private Boolean running = true;

    /**
     * TestTCS.begin() is the communication point between the testbed and the
     * TCS being tested on. Interactions between the testbed and TCS should all
     * be laid out in this method.
     */
    public void begin() {

        //TimedModeTest.run();
        //ResponsiveTest.run();
        //EmergencyModeTest.run();

    }

    /*
     * This is the old begin method. I kept it here for reference
     */
    public void testBegin() {

        /*
        RED (Color.RED),
        YELLOW (Color.YELLOW),
        GREEN (Color.GREEN),
        BLACK (Color.BLACK);

        SignalColor is an enum holding possible signal colors.
         */
        SignalColor north_south_color, east_west_color,north_south_left_color,east_west_left_color ;

        /*
        This is a useful way of grouping lights by direction.
        Here we are grouping parallel directions north with south, and east with west.
         */
        LinkedList<Lanes> north_south = new LinkedList<>();
        LinkedList<Lanes> east_west = new LinkedList<>();
        LinkedList<Lanes> east_west_left = new LinkedList<>();
        LinkedList<Lanes> north_south_left = new LinkedList<>();


        for(Lanes l: Lanes.values())
        {
            System.out.println(l.toString());
            if(l.toString().contains("N") || l.toString().contains("S")) {
                if(l.toString().contains("1")) {
                    north_south_left.add(l);
                }
                else{
                    north_south.add(l);
                }
            }
            else {
                if(l.toString().contains("1")) {
                    east_west_left.add(l);
                }
                else{
                    east_west.add(l);
                }
            }
        }

        while(running){
            /*
            This is a simple way of alternating the states of signal colors on a timed basis.
             */

            //Assign Times for each line keep the states of left turnes seperate
            //Total of

            if (count %  8 ==  0){
                north_south_color = SignalColor.GREEN;
                east_west_color = SignalColor.RED;
                north_south_left_color = SignalColor.RED;
                east_west_left_color = SignalColor.RED;
            } else if (count % 8 == 1){
                north_south_color = SignalColor.YELLOW;
                east_west_color = SignalColor.RED;
                north_south_left_color = SignalColor.RED;
                east_west_left_color = SignalColor.RED;
            } else if (count % 8 == 2){
                north_south_color = SignalColor.RED;
                east_west_color = SignalColor.RED;
                north_south_left_color = SignalColor.GREEN;
                east_west_left_color = SignalColor.RED;
            }
            else if (count % 8 == 3){
                north_south_color = SignalColor.RED;
                east_west_color = SignalColor.RED;
                north_south_left_color = SignalColor.YELLOW;
                east_west_left_color = SignalColor.RED;
            }
            else if (count % 8 == 4){
                north_south_color = SignalColor.RED;
                east_west_color = SignalColor.GREEN;
                north_south_left_color = SignalColor.RED;
                east_west_left_color = SignalColor.RED;
            }
            else if (count % 8 == 5){
                north_south_color = SignalColor.RED;
                east_west_color = SignalColor.YELLOW;
                north_south_left_color = SignalColor.RED;
                east_west_left_color = SignalColor.RED;
            }
            else if (count % 8 == 6){
                north_south_color = SignalColor.RED;
                east_west_color = SignalColor.RED;
                north_south_left_color = SignalColor.RED;
                east_west_left_color = SignalColor.GREEN;
            }
            else {
                north_south_color = SignalColor.RED;
                east_west_color = SignalColor.RED;
                north_south_left_color = SignalColor.RED;
                east_west_left_color = SignalColor.YELLOW;
            }

            /*
            This changes our grouping of lanes to the colors specified above.
             */
            for(Lanes l: north_south) {
                if (l.toString().contains("2") || l.toString().contains("3")) {
                    l.setColor(north_south_color);
                    Lights.WEST.setColor(SignalColor.GREEN);
                    Lights.EAST.setColor(SignalColor.GREEN);
                    Lights.NORTH.setColor(SignalColor.RED);
                    Lights.SOUTH.setColor(SignalColor.RED);
                }

            }
            for(Lanes l: east_west)
            {
                if (l.toString().contains("2") || l.toString().contains("3")) {
                    l.setColor(east_west_color);
                    Lights.NORTH.setColor(SignalColor.GREEN);
                    Lights.SOUTH.setColor(SignalColor.GREEN);
                    Lights.WEST.setColor(SignalColor.RED);
                    Lights.EAST.setColor(SignalColor.RED);
                }

            }
            for(Lanes l: north_south_left) {
                if (l.toString().contains("1")) {
                    l.setColor(north_south_left_color);
                    Lights.NORTH.setColor(SignalColor.RED);
                    Lights.SOUTH.setColor(SignalColor.RED);
                    Lights.WEST.setColor(SignalColor.RED);
                    Lights.EAST.setColor(SignalColor.RED);
                }
            }
            for(Lanes l: east_west_left)
            {
                if (l.toString().contains("1")) {
                    l.setColor(east_west_left_color);
                    Lights.NORTH.setColor(SignalColor.RED);
                    Lights.SOUTH.setColor(SignalColor.RED);
                    Lights.WEST.setColor(SignalColor.RED);
                    Lights.EAST.setColor(SignalColor.RED);
                }
            }


            count ++;


            testSensors();

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Test ended..");
    }

    public void end(){
        running = false;
    }

    /*
    Loops over the ped crosswalks and car lanes to
    print if each has traffic waiting on it
    */
    private void testSensors() {
        for (Lanes l : Lanes.values()) {
            System.out.println(l.toString() + " has car waiting: " + l.isCarOnLane());
        }

        for (Lights l : Lights.values()){
            System.out.println(l.toString() + " has ped waiting: " + l.isPedestrianAt());
        }
    }

}
