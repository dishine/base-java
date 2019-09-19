package com.shinedi.javabase.common.util;



import java.util.List;


public class MapUtil {

    /**
     * 按矩形计算中心点 返回中心点经纬度
     * @param positions
     * @return
     */
    public static Position getCenterPosByRectangle(List<Position> positions) {
        if (positions == null || positions.size() < 2) {
            return null;
        }
        Position first = positions.get(0);
        double maxLat = first.getLat();
        double maxLng = first.getLng();
        double minLat = first.getLat();
        double minLng = first.getLng();
        for (Position position : positions) {
            maxLat = Math.max(maxLat, position.getLat());
            maxLng = Math.max(maxLng, position.getLng());
            minLat = Math.min(minLat, position.getLat());
            minLng = Math.min(minLng, position.getLng());
        }
        if (maxLat == minLat && maxLng == minLng) {
            return new Position(minLng, minLat);
        }
        return new Position((maxLng + minLng) / 2, (maxLat + minLat) / 2);
    }




    public static class Position {

        private double lng;

        private double lat;

        public Position() {
        }

        public Position(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "lng=" + lng +
                    ", lat=" + lat +
                    '}';
        }

    }

}
