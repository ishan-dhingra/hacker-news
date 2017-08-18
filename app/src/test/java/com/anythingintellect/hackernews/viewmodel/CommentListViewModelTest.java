package com.anythingintellect.hackernews.viewmodel;

import com.anythingintellect.hackernews.BaseTest;
import com.anythingintellect.hackernews.util.MockData;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class CommentListViewModelTest extends BaseTest {

    CommentListViewModel commentListViewModel;

    @Before
    public void setup() {
        commentListViewModel = new CommentListViewModel(null);
    }

    // Should load comments for given ids
    @Test
    public void shouldLoadCommentsForGivenIds() {
        long[] kids = MockData.getKidsArray();
        commentListViewModel.loadComments(kids);
        assertEquals(commentListViewModel.getComments().size(), kids.length);
        assertFalse(commentListViewModel.getNoComments().get());
    }
    // Should show No Comment for null or empty array
    @Test
    public void shouldShowNoCommentForEmptyOrNullIds() {
        commentListViewModel.loadComments(null);
        assertTrue(commentListViewModel.getNoComments().get());
    }


}
