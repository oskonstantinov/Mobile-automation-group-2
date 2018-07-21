package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase
{
    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String tittle_before_rotation = ArticlePageObject.getArticleTittle();

        this.rotateScreenLandscape();

        String tittle_after_rotation = ArticlePageObject.getArticleTittle();

        assertEquals(
                "Article tittle have been changed after screen rotation",
                tittle_before_rotation,
                tittle_after_rotation
        );

        this.rotateScreenPortrait();

        String tittle_after_second_rotation = ArticlePageObject.getArticleTittle();

        assertEquals(
                "Article tittle have been changed after screen rotation",
                tittle_before_rotation,
                tittle_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackgroud()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

        this.backgroundApp(2);

        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
