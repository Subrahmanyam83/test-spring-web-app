package com.hooyu.exercise.controllers;

import net.icdpublishing.exercise2.searchengine.services.SearchEngineRetrievalService;

public class SearchController {
	private SearchEngineRetrievalService retrievalService;
	public SearchController(SearchEngineRetrievalService retrievalService) {
		this.retrievalService = retrievalService;
	}
}