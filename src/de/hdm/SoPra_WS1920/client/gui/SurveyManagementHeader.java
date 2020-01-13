package de.hdm.SoPra_WS1920.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.SoPra_WS1920.client.ClientsideSettings;
import de.hdm.SoPra_WS1920.shared.CinemaAdministrationAsync;
import de.hdm.SoPra_WS1920.shared.SurveyManagementAsync;
import de.hdm.SoPra_WS1920.shared.bo.Group;




public class SurveyManagementHeader extends FlowPanel {
	
	Label headline;
	Button createBo;
	Button createSurvey;
	SearchBox searchBox;
	
	GroupForm gf;
	SurveyCardEdit se;
	GroupCard groupCard;
	Group groupToShow;
	
	SurveyContent content;
	SurveyManagementAsync surveyManagementAdministration;
	
	
	public SurveyManagementHeader(SurveyContent content) {
		this.content = content;
	}
	
	
	public void onLoad() {
		super.onLoad();
		this.setStylePrimaryName("header");
		surveyManagementAdministration = ClientsideSettings.getSurveyManagement();
		//createBo.addClickHandler(new CreateBoClickHandler(this, gf));
		//createSurvey.addClickHandler(new CreateSurveyClickHandler(this, sf));
		
	}
		class CreateGroupClickHandler implements ClickHandler{
			SurveyManagementHeader header;
			SurveyContent content;
			GroupForm gf;
			
			public CreateGroupClickHandler(SurveyManagementHeader header, SurveyContent content) {
				this.header = header;
				this.content = content;
				
						
			}
			
		

			@Override
			public void onClick(ClickEvent event) {
				GroupForm gf = new GroupForm(header, content);
				gf.show();
				gf.center();
				
			}
		
		}
		
		class CreateSurveyClickHandler implements ClickHandler{
			SurveyManagementHeader header;
			SurveyContent content;
			SurveyCardEdit se;
			
			public CreateSurveyClickHandler(SurveyManagementHeader header, SurveyCardEdit se) {
			this.header = header;
			this.content = content;
			this.se = se;
			}

			@Override
			public void onClick(ClickEvent event) {
				SurveyCardEdit se = new SurveyCardEdit(content, header);
				se.center();
				se.show();
				
				
				
			}
			
		}
			
		
		class SearchBox extends FlowPanel {
			TextBox searchText;
			Button submitSearch;
			Image searchIcon;

		
		public void onLoad() {
			super.onLoad();
			this.setStylePrimaryName("searchBox");
			
			searchText = new TextBox();
			searchText.setStylePrimaryName("searchText");
			searchText.getElement().setPropertyString("placeholder", "Search...");
			
			
			submitSearch = new Button();

			searchIcon = new Image("/Images/search.png");
			searchIcon.setStyleName("searchIcon");

			this.add(searchIcon);
			this.add(searchText);
		}
		
		}
		
		public void searchWord(SearchBox searchBox, SurveyManagementHeader header){
			if(header.headline.getText().equals("Movies"));{
				
			}
			
		}
		
		public void showGroupHeader() {
			this.clear();
			headline = new Label("Groups");
			headline.setStylePrimaryName("headline");
			
			searchBox = new SearchBox();
			
			createBo = new Button("+Add Group");
			createBo.setStylePrimaryName("CreateBoButton");
			createBo.addClickHandler(new CreateGroupClickHandler(this, content));
			
			this.add(headline);
			this.add(createBo);
			this.add(searchBox);
		}
		

		
		public void showSurveyHeader() {
			this.clear();
			headline = new Label("Survey");
			headline.setStylePrimaryName("headline");
			
			searchBox = new SearchBox();
			
			createBo = new Button("+Add Survey");
			createBo.setStylePrimaryName("CreateBoButton");
			createBo.addClickHandler(new CreateSurveyClickHandler(this, se));
			
			this.add(headline);
			this.add(createBo);
			this.add(searchBox);
		}
		

		public void showMoviesHeader(){
			this.clear();
			headline = new Label("Movies");
			headline.setStylePrimaryName("headline");
			
			searchBox = new SearchBox();
			
			this.add(headline);
		}
		
		}
		

			
		
	


