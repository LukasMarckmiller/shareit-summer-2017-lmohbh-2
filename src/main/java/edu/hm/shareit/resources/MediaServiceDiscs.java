package edu.hm.shareit.resources;

import edu.hm.fachklassen.Disc;
import edu.hm.fachklassen.Medium;

/**
 * Created by oliver on 12.04.17.
 */
public interface MediaServiceDiscs {

    MediaServiceResultDiscs addDisc(Disc disc);

    Medium[] getDiscs();

    MediaServiceResultDiscs updateDisc(Disc disc);
}
