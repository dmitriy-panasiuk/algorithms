import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private Color[][] colors;
    private double[][] energies;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.colors = new Color[picture.width()][picture.height()];
        this.energies = new double[picture.width()][picture.height()];

        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                Color currentColor = picture.get(i, j);
                colors[i][j] = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            }
        }

        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                if (i == 0 || j == 0 || i == picture.width() - 1 || j == picture.height() - 1) {
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
        return this.picture.width();
    }

    public int height() {
        return this.picture.height();
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
        throw new UnsupportedOperationException();
    }

    public int[] findVerticalSeam() {
        throw new UnsupportedOperationException();
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
        Picture p = new Picture("3x4.png");
        SeamCarver s = new SeamCarver(p);
        int x = 1, y = 2;
        System.out.println(p.get(x, y).getRed() + " " + p.get(x, y).getGreen() + " " + p.get(x, y).getBlue());
        System.out.println(s.energy(x, y));
    }
}
