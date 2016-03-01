package com.lansun.qmyo.maijie_biz.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lansun.qmyo.maijie_biz.asynctask.MaijieBizHttpTask;
import com.lansun.qmyo.maijie_biz.asynctask.MaijieBizRqstApi;
import com.lansun.qmyo.maijie_biz.asynctask.http.BaseTask;
import com.lansun.qmyo.maijie_biz.asynctask.http.FetchListener;
import com.lansun.qmyo.maijie_biz.asynctask.http.TaskResult;
import com.lansun.qmyo.maijie_biz.asynctask.http.TaskResultStatus;
import com.lansun.qmyo.maijie_biz.asynctask.http.TaskSimpleListener;
import com.lansun.qmyo.maijie_biz.bean.BidDetail;
import com.lansun.qmyo.maijie_biz.bean.Bidding;
import com.lansun.qmyo.maijie_biz.bean.BiddingHistory;
import com.lansun.qmyo.maijie_biz.bean.Brand;
import com.lansun.qmyo.maijie_biz.bean.CarGift;
import com.lansun.qmyo.maijie_biz.bean.CompetingCar;
import com.lansun.qmyo.maijie_biz.bean.CustomerGroup;
import com.lansun.qmyo.maijie_biz.bean.CustomerInfo;
import com.lansun.qmyo.maijie_biz.bean.Dealer;
import com.lansun.qmyo.maijie_biz.bean.DownloadItem;
import com.lansun.qmyo.maijie_biz.bean.Inquiry;
import com.lansun.qmyo.maijie_biz.bean.Rank;
import com.lansun.qmyo.maijie_biz.bean.ReducePrice;
import com.lansun.qmyo.maijie_biz.bean.SysMessage;
import com.lansun.qmyo.maijie_biz.bean.User;

public class DataFetchManager {

      private static DataFetchManager _instance;

      private DataFetchManager() {
      }

      public static DataFetchManager getInstance() {
	  if (_instance == null)
	        _instance = new DataFetchManager();
	  return _instance;
      }

      /**
       * 登录
       * 
       * @param phoneNum
       * @param pwd
       * @param listener
       */
      public void fetchLogin(String phoneNum, String pwd, final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().doLogin(phoneNum, pwd);
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      User user = null;
			      if (jsonObj.has("d")) {
				  JSONObject jsonUser = jsonObj.getJSONObject("d");

				  user = new User();
				  user.parseJsonObj(jsonUser);

			      }

			      listener.onPostFetch(FetchListener.STATUS_OK, user);

			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });

	  task.execute();
      }

      /**
       * 注册获取短信验证码
       * 
       * @param phoneNum
       * @param listener
       */
      public void fetchPhoneVerify(String phoneNum, final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().getPhoneVerify(phoneNum);
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      String verifyNum = null;
			      if (jsonObj.has("d")) {
				  verifyNum = jsonObj.getString("d");
			      }

			      listener.onPostFetch(FetchListener.STATUS_OK, verifyNum);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}

		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });
	  task.execute();
      }

     

      /**
       * 注册下一步
       * 
       * @param phone
       * @param verify
       * @param passwd
       * @param listener
       */
      public void fetchRegisterNextStep(String phone, String verify, String passwd, final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().doRegisterNextStep(phone, verify, passwd);
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      String phoneNum = null;
			      String uID = null;
			      String passwd = null;
			      if (jsonObj.has("d")) {
				  JSONObject jResult = jsonObj.getJSONObject("d");
				  phoneNum = jResult.getString("phoneNum");
				  uID = jResult.getString("id");
				  passwd = jResult.getString("passwd");
			      }

			      listener.onPostFetch(FetchListener.STATUS_OK, phoneNum, uID, passwd);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}

		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });
	  task.execute();
      }

      /**
       * 注册提交 申请开通
       * 
       * @param user
       * @param listener
       */
      public void fetchRegisterSubmit(User user, final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().doRegisterSubmit(user);
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {

			      listener.onPostFetch(FetchListener.STATUS_OK, result);

			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });

	  task.execute();
      }

     

     
     
      

     

      
      /**
       * 提交问题反馈
       * 
       * @param feedMsg
       * @param listener
       */
      public void fetchFeedBack(String feedMsg, final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().doSubmitFeedBack(feedMsg);
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {

			      listener.onPostFetch(FetchListener.STATUS_OK, result);

			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });

	  task.execute();
      }

      /**
       * 获取系统消息
       * 
       * @param listener
       */
      public void fetchSysMsg(final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().getSysMsg();
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {

			      JSONObject jsonObj = (JSONObject) result.result;
			      List<SysMessage> msgList = null;
			      if (jsonObj.has("d")) {
				  msgList = new ArrayList<SysMessage>();
				  JSONArray msgArray = jsonObj.getJSONArray("d");

				  for (int i = 0; i < msgArray.length(); i++) {
				        JSONObject jObj = msgArray.getJSONObject(i);
				        SysMessage msg = new SysMessage();
				        boolean bParse = msg.parseJsonObj(jObj);

				        if (bParse)
					    msgList.add(msg);
				  }
			      }

			      listener.onPostFetch(FetchListener.STATUS_OK, msgList);

			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });

	  task.execute();
      }
      
      
      /**
       * 获取系统消息的数目
       * 
       * @param listener
       */
      public void fetchSysMsgCount(final FetchListener listener) {
    	  
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().getSysMsgCount();
	  
	  //给Task设置上监听器：TaskSimpleListener，但具体的TaskSimpleListener的方法由(final) FetchListener listener 在各自的载体类中 自定义具体的方法内容
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {

			      JSONObject jsonObj = (JSONObject) result.result;
			      String msgCount = null;
			      if (jsonObj.has("d")) {
				  msgCount = jsonObj.getString("d");
			      }

			      //各自界面执行具体的 页面操作的情况
			      listener.onPostFetch(FetchListener.STATUS_OK, msgCount);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });

	  task.execute();
      }

    

     

    
     
     



      public void fetchUpdateInfo(final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().getUpdateInfo();
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      DownloadItem item = null;
			      if (jsonObj.has("d")) {
				  JSONObject downObj = jsonObj.getJSONObject("d");
				  item = new DownloadItem();
				  item.parseJsonObj(downObj);
			      }

			      listener.onPostFetch(FetchListener.STATUS_OK, item);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });

	  task.execute();
      }

      public void fetchFindBackPasswd(String phone, String verify, String passwd, final FetchListener listener) {
	  MaijieBizHttpTask task = MaijieBizRqstApi.getInstance().doFindBackPasswd(phone, verify, passwd);
	  task.setListener(new TaskSimpleListener() {

	        @Override
	        public void onPreExecute(BaseTask task) {
		    listener.onPreFetch();
	        }

	        @Override
	        public void onTaskFinished(BaseTask task, TaskResult result) {
		    if (result.status == TaskResultStatus.OK) {
			
		    	//此处进行了解析的操作，以一个Object的对象TaskResult（extends Object）进行返回
		    	try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      User user = null;
			      if (jsonObj.has("d")) {
				  user = new User();
				  JSONObject userObj = jsonObj.getJSONObject("d");
				  user.u_passwd = userObj.getString("passwd");
				  user.u_id = userObj.getString("id");
				  user.u_phonenum = userObj.getString("phoneNum");
			      }

			      listener.onPostFetch(FetchListener.STATUS_OK, user);
			} catch (Exception e) {
			      e.printStackTrace();
			      //result依旧将处理过后的result值传送过去
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}

		    } else {
			try {
			      JSONObject jsonObj = (JSONObject) result.result;
			      JSONObject dataObj = jsonObj.getJSONObject("d");
			      String errMsg = dataObj.getString("m");
			      int errCode = Integer.parseInt(dataObj.getString("c"));
			      listener.onPostFetch(FetchListener.STATUS_NET_ERROR, errMsg, errCode);
			} catch (Exception e) {
			      e.printStackTrace();
			      listener.onPostFetch(FetchListener.STATUS_PARSER_ERROR, result);
			}
		    }
	        }
	  });
	  task.execute();
      }

      /**
       * final FetchListener listener 对象已经存在，供调用处进行View层面的具体操作
       */



}
