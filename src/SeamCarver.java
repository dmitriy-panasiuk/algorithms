import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private Color[][] colors;
    private double[][] energies;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new NullPointerException();

        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
        this.colors = new Color[this.width][this.height()];
        this.energies = new double[this.width][this.height()];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Color currentColor = picture.get(i, j);
                colors[i][j] = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            }
        }

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (i == 0 || j == 0 || i == this.width - 1 || j == this.height - 1) {
                    energies[i][j] = 1000.0;
                } else {
                    energies[i][j] = Math.sqrt(gradientX(i, j) + gradientY(i, j));
                }
            }
        }
    }

    public Picture picture() {
        return this.picture;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public double energy(int x, int y) {
        return this.energies[x][y];
    }

    private double gradientX(int x, int y) {
        double dR = Math.abs(colors[x - 1][y].getRed() - colors[x + 1][y].getRed());
        double dG = Math.abs(picture.get(x - 1, y).getGreen() - picture.get(x + 1, y).getGreen());
        double dB = Math.abs(picture.get(x - 1, y).getBlue() - picture.get(x + 1, y).getBlue());

        return dR * dR + dG * dG + dB * dB;
    }

    private double gradientY(int x, int y) {
        double dR = Math.abs(picture.get(x, y - 1).getRed() - picture.get(x, y + 1).getRed());
        double dG = Math.abs(picture.get(x, y - 1).getGreen() - picture.get(x, y + 1).getGreen());
        double dB = Math.abs(picture.get(x, y - 1).getBlue() - picture.get(x, y + 1).getBlue());

        return dR * dR + dG * dG + dB * dB;
    }

    public int[] findHorizontalSeam() {
        double[][] paths = new double[width()][height()];
        double min = Double.POSITIVE_INFINITY;
        int indexMin = 0;
        int[] result = new int[width()];

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (x == 0) {
                    paths[x][y] = energies[x][y];
                } else {
                    if (y == 0) {
                        paths[x][y] = energies[x][y] + Math.min(paths[x - 1][y], paths[x - 1][y + 1]);
                    } else if (y == height() - 1) {
                        paths[x][y] = energies[x][y] + Math.min(paths[x - 1][y], paths[x - 1][y - 1]);
                    } else {
                        paths[x][y] = energies[x][y] + smallestOfThree(paths[x - 1][y - 1], paths[x - 1][y], paths[x - 1][y + 1]);
                    }
                }
            }
        }

        for (int y = 0; y < height(); y++) {
            if (paths[width()-1][y] <= min) {
                min = paths[width()-1][y];
                indexMin = y;
            }
        }
        result[width() - 1] = indexMin;
        for (int x = width() - 2; x >= 0; x--) {
            int currentMin = result[x + 1];
            if (currentMin == 0) {
                result[x] = indexYOfSmallestElement(paths, x, currentMin, currentMin + 1);
            } else if (currentMin == width() - 1) {
                result[x] = indexYOfSmallestElement(paths, x, currentMin - 1, currentMin);
            } else {
                result[x] = indexYOfSmallestElement(paths, x, currentMin - 1, currentMin, currentMin + 1);
            }
        }

        return result;
    }

    public int[] findVerticalSeam() {
        throw new UnsupportedOperationException();
    }

    private int indexYOfSmallestElement(double[][] paths, int x, int... elems) {
        double min = Double.POSITIVE_INFINITY;
        int minIndex = elems[0];

        for (int i : elems) {
            if (paths[x][i] < min) {
                min = paths[x][i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private double smallestOfThree(double a, double b, double c) {
        return Math.min(a, Math.min(b, c));
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        if (width() <= 1 || height() <= 1) {
            throw new IllegalArgumentException();
        }
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        if (width() <= 1 || height() <= 1) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Picture p = new Picture("6x5.png");
        SeamCarver s = new SeamCarver(p);
        int x = 1, y = 2;
        System.out.println(p.get(x, y).getRed() + " " + p.get(x, y).getGreen() + " " + p.get(x, y).getBlue());
        System.out.println(s.energy(x, y));
        s.findVerticalSeam();
    }
}
