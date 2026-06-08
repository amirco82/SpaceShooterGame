package my_base;

import base.PeriodicLoop;

public class MyPeriodicLoop extends PeriodicLoop {
    
    // ac: execute periodic tasks
    @Override
    public void execute() {
        super.execute();
        // ac: intentionally left blank for now
        // ac: will be used later for moving bullets and enemies
    }
}
/******************************** */
// package my_base;

// import base.PeriodicLoop;

// public class MyPeriodicLoop extends PeriodicLoop {

// 	private AppContent content = App.content();
	
// 	@Override
// 	public void execute() {
// 		// Let the super class do its work first
// 		super.execute();
		
// 		// Then do your own work here ...
// 		content.ex_Backend().moveCircle2(10, 0);
		
// 	}

// }
