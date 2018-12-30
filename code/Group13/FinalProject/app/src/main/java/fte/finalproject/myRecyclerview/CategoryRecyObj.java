package fte.finalproject.myRecyclerview;

public class CategoryRecyObj {
    private String categoryName;
    private int bookCount;

    public CategoryRecyObj(String categoryName, int bookCount) {
        this.categoryName = categoryName;
        this.bookCount = bookCount;
    }

    public int getBookCount() {
        return bookCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
