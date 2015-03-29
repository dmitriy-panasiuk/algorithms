public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
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
        double gX, gY;
        if (x < 0 || x >= width()) {
            throw new IndexOutOfBoundsException();
        }
        if (y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException();
        }

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return 195075.0;
        }
        gX = gradientX(x, y);
        gY = gradientY(x, y);

        return gX + gY;
    }

    private double gradientX(int x, int y) {
        double dR = Math.abs(picture.get(x - 1, y).getRed() - picture.get(x + 1, y).getRed());
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
        Picture p = new Picture("6x5.png");
        SeamCarver s = new SeamCarver(p);
        int x = 4, y = 4;
        System.out.println(p.get(x,y).getRed() + " " + p.get(x,y).getGreen() + " " + p.get(x,y).getBlue());
        System.out.println(s.energy(x,y));
    }
}
