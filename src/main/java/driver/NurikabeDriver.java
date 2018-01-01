package driver;

import AI.NuriSolver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.NuriWebInterface;

public class NurikabeDriver {
    public static void main(String[] whatever){
        NuriWebInterface nws = new NuriWebInterface(new ChromeDriver());
        NuriSolver.debug = true;
        new NuriSolver(nws).run();
    }
}
