package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizPreferences;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Sep 10, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLVizViewOptions {

//	private static OWLVizViewOptions instance;

	private boolean displayAnonymousClasses = false;
	private boolean displayIndividuals = false;
	private boolean groupClassesByNameSpace = false;
	private boolean displayDisjointClassIndicator = true;
	private boolean displayIsALabels = false;
	private double edgeBrightness = 0.5;
    private boolean trackerMode;

	private ArrayList<OptionsChangedListener> listeners;

    private int trackerRadius;


    public OWLVizViewOptions() {
		listeners = new ArrayList<OptionsChangedListener>();
        trackerRadius = OWLVizPreferences.getInstance().getDefaultTrackerRadius();
        trackerMode = OWLVizPreferences.getInstance().isTrackingModeDefault();
    }


//	public static synchronized OWLVizViewOptions getInstance() {
//		if(instance == null) {
//			instance = new OWLVizViewOptions();
//		}
//		return instance;
//	}


	public boolean isDisplayAnonymousClasses() {
		return displayAnonymousClasses;
	}


	public void setDisplayAnonymousClasses(boolean displayAnonymousClasses) {
		this.displayAnonymousClasses = displayAnonymousClasses;
		if(displayAnonymousClasses == true) {
			displayIndividuals = true;
		}
		fireOptionsChangedEvent();
	}


	public boolean isDisplayIndividuals() {
		return displayIndividuals;
	}


	public void setDisplayIndividuals(boolean displayIndividuals) {
		if(displayAnonymousClasses == true) {
			displayIndividuals = true;
		}
		else {
			this.displayIndividuals = displayIndividuals;
		}
		fireOptionsChangedEvent();
	}


	public boolean isGroupClassesByNameSpace() {
		return groupClassesByNameSpace;
	}


	public void setGroupClassesByNameSpace(boolean groupClassesByNameSpace) {
		this.groupClassesByNameSpace = groupClassesByNameSpace;
		fireOptionsChangedEvent();
	}


	public boolean isDisplayDisjointClassIndicator() {
		return displayDisjointClassIndicator;
	}


	public void setDisplayDisjointClassIndicator(boolean displayDisjointClassIndicator) {
		this.displayDisjointClassIndicator = displayDisjointClassIndicator;
		fireOptionsChangedEvent();
	}


	public double getEdgeBrightness() {
		return edgeBrightness;
	}


	public void setEdgeBrightness(double edgeBrightness) {
		this.edgeBrightness = edgeBrightness;
		fireOptionsChangedEvent();
	}


	public boolean isDisplayIsALabels() {
		return displayIsALabels;
	}


	public void setDisplayIsALabels(boolean displayIsALabels) {
		this.displayIsALabels = displayIsALabels;
	}


	public void addOptionsChangedListener(OptionsChangedListener lsnr) {
		if(!listeners.contains(lsnr)) {
			listeners.add(lsnr);
		}
	}


	public void removeOptionsChangedListener(OptionsChangedListener lsnr) {
		listeners.remove(lsnr);
	}


	protected void fireOptionsChangedEvent() {
		Iterator<OptionsChangedListener> it = listeners.iterator();
		while(it.hasNext()) {
		    it.next().optionsChanged();
		}
	}


    public boolean isTrackerMode() {
        return trackerMode;
    }


    public void setTrackerMode(boolean trackerMode) {
        this.trackerMode = trackerMode;
        fireOptionsChangedEvent();
    }


    public int getTrackerRadius() {
        return trackerRadius;
    }


    public void setTrackerRadius(int trackerRadius) {
        this.trackerRadius = trackerRadius;
         fireOptionsChangedEvent();
    }
}

