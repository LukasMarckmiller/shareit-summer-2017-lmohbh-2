package edu.hm.api;

import edu.hm.fachklassen.Disc;
import edu.hm.fachklassen.Medium;

/**
 * Created by oliver on 19.04.17.
 */
public class MediaServiceImplDiscs implements MediaServiceDiscs{


    @Override
    public MediaServiceResultDiscs addDisc(Disc disc) {
        return null;
    }

    @Override
    public Medium[] getDiscs() {
        return new Medium[0];
    }

    @Override
    public MediaServiceResultDiscs updateDisc(Disc disc) {
        return null;
    }
}
