package edu.hm.shareit.resources;

import edu.hm.fachklassen.Disc;

/**
 * Created by oliver on 12.04.17.
 */
public interface DiscService {
    DiscServiceResult addDisc(Disc disc);

    Disc getDisc(String barcode);

    Disc[] getDiscs();

    DiscServiceResult updateDisc(Disc disc);
}
