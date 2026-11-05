package fr.robie.craftengineconverter.api.progress;

import java.util.Objects;

public final class ProgressBarOption implements ProgressBarUtils {

    private static final BukkitProgressBar.ProgressColor DEFAULT_PROGRESS = BukkitProgressBar.ProgressColor.GREEN;
    private static final BukkitProgressBar.ProgressColor DEFAULT_EMPTY = BukkitProgressBar.ProgressColor.DARK_GRAY;
    private static final BukkitProgressBar.ProgressColor DEFAULT_PERCENT = BukkitProgressBar.ProgressColor.YELLOW;
    private static final char DEFAULT_PROGRESS_CHAR = '█';
    private static final char DEFAULT_EMPTY_CHAR = '░';
    private static final int DEFAULT_BAR_WIDTH = 50;

    private BukkitProgressBar.ProgressColor progressColor;
    private BukkitProgressBar.ProgressColor emptyColor;
    private BukkitProgressBar.ProgressColor percentColor;
    private char progressChar;
    private char emptyChar;
    private int barWidth;

    public ProgressBarOption(BukkitProgressBar.ProgressColor progressColor,
                             BukkitProgressBar.ProgressColor emptyColor,
                             BukkitProgressBar.ProgressColor percentColor,
                             char progressChar,
                             char emptyChar,
                             int barWidth) {
        this.progressColor = Objects.requireNonNull(progressColor, "progressColor");
        this.emptyColor = Objects.requireNonNull(emptyColor, "emptyColor");
        this.percentColor = Objects.requireNonNull(percentColor, "percentColor");
        if (barWidth <= 0) throw new IllegalArgumentException("barWidth must be positive");
        this.progressChar = progressChar;
        this.emptyChar = emptyChar;
        this.barWidth = barWidth;
    }

    public ProgressBarOption() {
        this(DEFAULT_PROGRESS, DEFAULT_EMPTY, DEFAULT_PERCENT, DEFAULT_PROGRESS_CHAR, DEFAULT_EMPTY_CHAR, DEFAULT_BAR_WIDTH);
    }

    public static ProgressBarOption defaults() {
        return new ProgressBarOption();
    }

    public static ProgressBarOption of(BukkitProgressBar.ProgressColor progressColor) {
        return new ProgressBarOption(
                progressColor == null ? DEFAULT_PROGRESS : progressColor,
                DEFAULT_EMPTY,
                DEFAULT_PERCENT,
                DEFAULT_PROGRESS_CHAR,
                DEFAULT_EMPTY_CHAR,
                DEFAULT_BAR_WIDTH
        );
    }

    @Override
    public BukkitProgressBar.ProgressColor getProgressColor() {
        return this.progressColor;
    }

    @Override
    public BukkitProgressBar.ProgressColor getEmptyColor() {
        return this.emptyColor;
    }

    @Override
    public BukkitProgressBar.ProgressColor getPercentColor() {
        return this.percentColor;
    }

    @Override
    public char getProgressChar() {
        return this.progressChar;
    }

    @Override
    public char getEmptyChar() {
        return this.emptyChar;
    }

    @Override
    public int getBarWidth() {
        return this.barWidth;
    }

    // Setters return a new instance (immutable fluent API)
    @Override
    public ProgressBarUtils setProgressColor(BukkitProgressBar.ProgressColor color) {
        this.progressColor = color == null ? DEFAULT_PROGRESS : color;
        return this;
    }

    @Override
    public ProgressBarUtils setEmptyColor(BukkitProgressBar.ProgressColor color) {
        this.emptyColor = color == null ? DEFAULT_EMPTY : color;
        return this;
    }

    @Override
    public ProgressBarUtils setPercentColor(BukkitProgressBar.ProgressColor color) {
        this.percentColor = color == null ? DEFAULT_PERCENT : color;
        return this;
    }

    @Override
    public ProgressBarUtils setProgressChar(char progressChar) {
        this.progressChar = progressChar;
        return this;
    }

    @Override
    public ProgressBarUtils setEmptyChar(char emptyChar) {
        this.emptyChar = emptyChar;
        return this;
    }

    @Override
    public ProgressBarUtils setBarWidth(int barWidth) {
        if (barWidth <= 0) throw new IllegalArgumentException("barWidth must be positive");
        this.barWidth = barWidth;
        return this;
    }
}
