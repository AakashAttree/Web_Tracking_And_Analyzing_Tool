package web.tracking.workflow.action.referrer.handler.strategy.impl;

import web.tracking.db.dto.SearchStringDTO;
import web.tracking.db.dto.SearchWordDTO;
import web.tracking.db.dto.SocialMediaActivityDTO;
import web.tracking.workflow.action.referrer.handler.strategy.ReferrerURLHandlerStrategy;

public class DoNothingHandler extends ReferrerURLHandlerStrategy {

  public DoNothingHandler(String name, String parameterName) {
    super(name, parameterName);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected void preSave(SearchStringDTO searchStringDTO) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void preSave(SearchWordDTO searchWordDTO) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void preSave(SocialMediaActivityDTO activityDTO) {
    // TODO Auto-generated method stub

  }

}
