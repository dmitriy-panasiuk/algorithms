import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private Color[][] colors;
    private double[][] energies;
    private int width;
    private int height;
    private boolean isColorsChanged;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new NullPointerException();

        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
        this.colors = new Color[this.width][this.height];
        this.energies = new double[this.width][this.height()];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Color currentColor = picture.get(i, j);
                colors[i][j] = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            }
        }

        this.energies = energiesFromColors();
    }

    private double[][] energiesFromColors() {
        double[][] energies = new double[this.width][this.height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (i == 0 || j == 0 || i == this.width - 1 || j == this.height - 1) {
                    energies[i][j] = 1000.0;
                } else {
                    energies[i][j] = Math.sqrt(gradientX(i, j) + gradientY(i, j));
                }
            }
        }

        return energies;
    }

    public Picture picture() {
        if (isColorsChanged) {
            Picture newPicture= new Picture(this.width, this.height);
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    newPicture.set(i, j, colors[i][j]);
                }
            }
            this.picture = newPicture;
            this.isColorsChanged = false;
        }

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
        double dG = Math.abs(colors[x - 1][y].getGreen() - colors[x + 1][y].getGreen());
        double dB = Math.abs(colors[x - 1][y].getBlue() - colors[x + 1][y].getBlue());

        return dR * dR + dG * dG + dB * dB;
    }

    private double gradientY(int x, int y) {
        double dR = Math.abs(colors[x][y - 1].getRed() - colors[x][y + 1].getRed());
        double dG = Math.abs(colors[x][y - 1].getGreen() - colors[x][y + 1].getGreen());
        double dB = Math.abs(colors[x][y - 1].getBlue() - colors[x][y + 1].getBlue());

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
            if (paths[width() - 1][y] < min) {
                min = paths[width() - 1][y];
                indexMin = y;
            }
        }
        result[width() - 1] = indexMin;
        for (int x = width() - 2; x >= 0; x--) {
            int currentMin = result[x + 1];
            if (currentMin == 0) {
                result[x] = indexYOfSmallestElement(paths, x, currentMin, currentMin + 1);
            } else if (currentMin == height() - 1) {
                result[x] = indexYOfSmallestElement(paths, x, currentMin - 1, currentMin);
            } else {
                result[x] = indexYOfSmallestElement(paths, x, currentMin - 1, currentMin, currentMin + 1);
            }
        }

        return result;
    }

    public int[] findVerticalSeam() {
        double[][] paths = new double[width()][height()];
        double min = Double.POSITIVE_INFINITY;
        int indexMin = 0;
        int[] result = new int[height()];

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                if (y == 0) {
                    paths[x][y] = energies[x][y];
                } else {
                    if (x == 0) {
                        paths[x][y] = energies[x][y] + Math.min(paths[x][y - 1], paths[x + 1][y - 1]);
                    } else if (x == width() - 1) {
                        paths[x][y] = energies[x][y] + Math.min(paths[x][y - 1], paths[x - 1][y - 1]);
                    } else {
                        paths[x][y] = energies[x][y] + smallestOfThree(paths[x - 1][y - 1], paths[x][y - 1], paths[x + 1][y - 1]);
                    }
                }
            }
        }

        for (int x = 0; x < width(); x++) {
            if (paths[x][height() - 1] < min) {
                min = paths[x][height() - 1];
                indexMin = x;
            }
        }
        result[height() - 1] = indexMin;
        for (int y = height() - 2; y >= 0; y--) {
            int currentMin = result[y + 1];
            if (currentMin == 0) {
                result[y] = indexXOfSmallestElement(paths, y, currentMin, currentMin + 1);
            } else if (currentMin == width() - 1) {
                result[y] = indexXOfSmallestElement(paths, y, currentMin - 1, currentMin);
            } else {
                result[y] = indexXOfSmallestElement(paths, y, currentMin - 1, currentMin, currentMin + 1);
            }
        }

        return result;
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

    private int indexXOfSmallestElement(double[][] paths, int y, int... elems) {
        double min = Double.POSITIVE_INFINITY;
        int minIndex = elems[0];

        for (int i : elems) {
            if (paths[i][y] < min) {
                min = paths[i][y];
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
        assureValidHorizontalSeam(seam);
        Color[][] newColors = new Color[this.width()][this.height() - 1];

        for (int x = 0; x < width(); x++) {
            int currentSeam = seam[x];
            int counter = 0;
            for (int y = 0; y < height(); y++) {
                if (y != currentSeam) {
                    newColors[x][counter] = colors[x][y];
                    counter++;
                }
            }
        }

        this.colors = newColors;
        this.height -= 1;
        this.energies = energiesFromColors();
        this.isColorsChanged = true;
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        assureValidVerticalSeam(seam);
        Color[][] newColors = new Color[this.width() - 1][this.height()];

        for (int y = 0; y < height(); y++) {
            int currentSeam = seam[y];
            int counter = 0;
            for (int x = 0; x < width(); x++) {
                if (x != currentSeam) {
                    newColors[counter][y] = colors[x][y];
                    counter++;
                }
            }
        }
        this.colors = newColors;
        this.width -= 1;
        this.energies = energiesFromColors();
        this.isColorsChanged = true;
    }

    private void assureValidVerticalSeam(int[] seam) {
        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || seam[i] > width() - 1 || Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        if (seam[seam.length - 1] < 0 || seam[seam.length - 1] > width() - 1) {
            throw new IllegalArgumentException();
        }
    }

    private void assureValidHorizontalSeam(int[] seam) {
        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || seam[i] > height() - 1 || Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        if (seam[seam.length - 1] < 0 || seam[seam.length - 1] > height() - 1) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Picture p = new Picture("6x5.png");
        SeamCarver s = new SeamCarver(p);
        int[] arr = {1, 1, 1, 1, 1, 1};
        s.removeHorizontalSeam(arr);
        s.removeHorizontalSeam(arr);
        s.picture().save("new.png");
    }
}
