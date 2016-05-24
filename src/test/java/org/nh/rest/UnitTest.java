package org.nh.rest;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;
import org.mockito.internal.listeners.CollectCreatedMocks;
import org.mockito.internal.progress.MockingProgress;
import org.mockito.internal.progress.ThreadSafeMockingProgress;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class UnitTest {

    public class MocksCollector {
        private final List<Object> createdMocks;

        public MocksCollector() {
            createdMocks = new LinkedList<Object>();
            final MockingProgress progress = new ThreadSafeMockingProgress();
            progress.setListener(new CollectCreatedMocks(createdMocks));
        }

        public Object[] getMocks() {
            return createdMocks.toArray();
        }
    }

    protected final MocksCollector mocksCollector = new MocksCollector();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mocksCollector.getMocks());
    }
}
