package web;

import grids.Coordinate;
import nurikabe.NuriBoard;
import nurikabe.NuriBoardLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class NuriWebInterface {
    private final WebDriver driver;
    private final int size;
    private final NuriBoard board;

    public NuriWebInterface(WebDriver driver, int sizeID){
        this.driver = driver;
        driver.navigate().to("https://www.puzzle-nurikabe.com/?size="+sizeID);
        this.size = getSize(sizeID);
        NuriBoardLoader nuriLoader = new NuriBoardLoader(readTable());
        this.board = new NuriBoard(size, size, nuriLoader::getSquare);
    }

    public NuriBoard getBoard(){
        return board;
    }

    public NuriWebInterface(WebDriver driver){
        this(driver, 4);
    }


    public int[][] readTable(){
        int [][] output = new int[size][size];
        WebElement nurikabeTable = driver.findElement(By.id("NurikabeTable"));
        String innerHTML = nurikabeTable.getAttribute("innerHTML");
        String[] rows = innerHTML.split("<tr>");
        for(int i=1; i<rows.length; i++){
            String[] squares = rows[i].split("<td>");
            for(int j=1; j<squares.length; j++){
                if(Character.isDigit(squares[j].charAt(0))){
                    output[j-1][i-1]=Integer.parseInt(squares[j].substring(0, squares[j].indexOf('<')));
                }
                else{
                    output[j-1][i-1]=0;
                }
            }
        }
        return output;
    }

    public static int getSize(int sizeID){
        switch (sizeID){
            case 0: return 5;
            case 1: return 7;
            case 2: return 10;
            case 5: return 12;
            case 3: return 15;
            case 4: return 20;
            default: throw new IllegalArgumentException("No game exists for sizeID "+sizeID);
        }
    }

    public void click(Coordinate c){
        getSquare(c).click();
    }

    public void rightClick(Coordinate c){
        Actions rightClick = new Actions(driver);
        rightClick.contextClick(getSquare(c)).build().perform();
    }

    private WebElement getSquare(Coordinate c){
        String xpath = "//table[@id='NurikabeTable']/tbody/tr["+(c.y+1)+"]/td["+(c.x+1)+"]";
        return driver.findElement(By.xpath(xpath));
    }

    public static void main(String[] args){
        NuriWebInterface nwi =  new NuriWebInterface(WebDriverManager.get());
        System.out.println(nwi.board);

        for(int i=0; i<nwi.size; i+=3){
            nwi.click(new Coordinate(6, i));
            nwi.rightClick(new Coordinate(i, 6));
        }
    }
}
