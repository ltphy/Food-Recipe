package com.duytue.finalproject;

import java.util.ArrayList;

import static java.util.Collections.swap;

/**
 * Created by Phy on 7/29/2017.
 */

public class PlaceHelper {
    double latitude;
    double longitude;

    public PlaceHelper(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //the crow flies
    ArrayList<Place> getNNearestPlace(int n, ArrayList<Place> list) {

        ArrayList<Place> result = new ArrayList<>();
        sortByDistanceDesc(list, 0, list.size() - 1);
        if (n < list.size()) {
            for (int i = 0; i < n; ++i) {

                result.add(list.get(i));
            }
        }
        return result;
    }

    public void sortByDistanceDesc(ArrayList<Place> list, int low, int high) {

        if (low < high) {
            int p = partition(list, low, high);
            sortByDistanceDesc(list, low, p - 1);
            sortByDistanceDesc(list, p + 1, high);
        }
    }

    double findDistance(Place place) {
        return Math.sqrt(Math.pow(this.latitude - place.latLng.latitude, 2) + Math.pow(this.longitude - place.latLng.longitude, 2));

    }

    int partition(ArrayList<Place> list, int low, int high) {
        double x = findDistance(list.get(high));
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (findDistance(list.get(j)) < x) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    void quickSortmiddlePivot(ArrayList<Place> list, int low, int high) {
        if (low < high) {
            double mid = findDistance(list.get((low + high) / 2));
            int i = low, j = high;
            while (i < j) {
                while (findDistance(list.get(i)) < mid) {
                    i++;
                }
                while (findDistance(list.get(j)) > mid) {
                    j--;
                }
                if (i <= j) {
                    swap(list, i, j);
                    i++;
                    j--;
                }
            }
            quickSortmiddlePivot(list, low, j);
            quickSortmiddlePivot(list, i, high);
        }

    }
}
