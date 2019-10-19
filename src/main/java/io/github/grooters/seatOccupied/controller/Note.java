package io.github.grooters.seatOccupied.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import io.github.grooters.seatOccupied.dao.ChildNoteDao;
import io.github.grooters.seatOccupied.dao.MainNoteDao;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.ChildNote;
import io.github.grooters.seatOccupied.model.ChildNotes;
import io.github.grooters.seatOccupied.model.MainNote;
import io.github.grooters.seatOccupied.model.MainNotes;
import io.github.grooters.seatOccupied.model.User;

@RestController
public class Note {
	// 获取成功返回1
	private static final String SUCCESS = "1";
	// 获取失败返回0
	private static final String FAILED = "0";
	@Autowired
	private MainNoteDao mainNoteDao;
	@Autowired
	private ChildNoteDao childNoteDao;
	@Autowired
	private UserDao userDao;

	// // 根据主帖名获取主帖
	// @RequestMapping(value = "/getMainPostForTitle", method =
	// RequestMethod.GET)
	// public String getMainNote1(String name) {
	// MainNote mNote = null;
	// mNote = mainNoteDao.findByName(name);
	// if (mNote != null) {
	// return JSON.toJSONString(mNote);
	// }
	// // 根据名称查找的帖子不存在
	// else
	// return FAILED;
	// }

	// 根据主帖id获取主帖
	@RequestMapping(value = "/getMainPostForId", method = RequestMethod.GET)
	public String getMainNote2(int id) {
//		MainNote mNote = null;
//		List<MainNote> mNotes = new ArrayList<MainNote>();
//		mNote = mainNoteDao.findById(id);
//		if (mNote != null) {
//			for (int i = id; i < id + 5; i++) {
//				mNote = mainNoteDao.findById(i);
//				// 此处是防止id不足5的情况
//				if (mNote != null) {
//					mNotes.add(mNote);
//				}
//			}
//			MainNotes mainNotes = new MainNotes();
//			mainNotes.setMainPosts(mNotes);
//			return JSON.toJSONString(mainNotes);
//		}
//		// 根据id查找的帖子不存在
//		else
//			return FAILED;
		List<MainNote> mNotes = new ArrayList<MainNote>();
		MainNotes mainNotes=new MainNotes();
		MainNote mNote;
		List<MainNote> allNotes=mainNoteDao.findAll();
		if(allNotes.size()>id){
			for (int i = 1; i < id+1; i++) {
				mNote = mainNoteDao.findById(i);
				mNotes.add(mNote);
			}
			mainNotes.setMainPosts(mNotes);
			return JSON.toJSONString(mainNotes);
		}else{
			mainNotes.setMainPosts(allNotes);
			return JSON.toJSONString(mainNotes);
		}
	}

	// 根据主帖标题获取主帖
	@RequestMapping(value = "/getMainPostForTitle", method = RequestMethod.GET)
	public String getMainNote3(String title) {
		MainNote mNote = null;
		mNote = mainNoteDao.findByTitle(title);
		if (mNote != null) {
			return JSON.toJSONString(mNote);
		}
		// 根据名称查找的帖子不存在
		else
			return FAILED;
	}

	// 根据主帖标题获取主帖
	@RequestMapping(value = "/getMainPosts", method = RequestMethod.GET)
	public String getMainPosts() {
		List<MainNote> notes;
		notes = mainNoteDao.findAll();
		if (notes != null) {
			return JSON.toJSONString(notes);
		}
		// 根据名称查找的帖子不存在
		else
			return FAILED;
	}


	// // 根据主帖的发表日期获取批量主帖
	// @RequestMapping(value = "/getmaind", method = RequestMethod.GET)
	// public String getMainNote4(String date) {
	// MainNote mNote = null;
	// List<MainNote> mNotes = new ArrayList<MainNote>();
	// mNotes = mainNoteDao.findByDate(date);
	// if (mNotes.size() != 0) {
	// // 如果序列长度为1，则直接返回主帖对象
	// if (mNotes.size() == 1) {
	// return JSON.toJSONString(mNotes.get(0));
	// }
	// return JSON.toJSONString(mNotes);
	// } else {
	// return FAILED;
	// }
	// }

	// // 根据子帖名获取子帖
	// @RequestMapping(value = "/getchilda", method = RequestMethod.GET)
	// public String getChildNote1(String name) {
	// ChildNote cNote = null;
	// cNote = childNoteDao.findByName(name);
	// if (cNote != null) {
	// return JSON.toJSONString(cNote);
	// }
	// // 根据名称查找的帖子不存在
	// else
	// return FAILED;
	// }

	// // 根据子帖id获取主帖
	// @RequestMapping(value = "/getchildb", method = RequestMethod.GET)
	// public String getChildNote2(int id) {
	// ChildNote cNote = null;
	// cNote = childNoteDao.findById(id);
	// if (cNote != null) {
	// return JSON.toJSONString(cNote);
	// }
	// // 根据id查找的帖子不存在
	// else
	// return FAILED;
	// }
	//
	// // 根据子帖标题获取主帖
	// @RequestMapping(value = "/getchildc", method = RequestMethod.GET)
	// public String getChildNote3(String title) {
	// ChildNote cNote = null;
	// cNote = childNoteDao.findByName(title);
	// if (cNote != null) {
	// return JSON.toJSONString(cNote);
	// }
	// // 根据名称查找的帖子不存在
	// else
	// return FAILED;
	// }
	//
	// // 根据子帖的发表日期获取批量子帖
	// @RequestMapping(value = "/getchildd", method = RequestMethod.GET)
	// public String getChildNote4(String date) {
	// ChildNote cNote = null;
	// List<ChildNote> cNotes = new ArrayList<ChildNote>();
	// cNotes = childNoteDao.findByDate(date);
	// if (cNotes.size() != 0) {
	// // 如果序列长度为1，则直接返回主帖对象
	// if (cNotes.size() == 1) {
	// return JSON.toJSONString(cNotes.get(0));
	// }
	// return JSON.toJSONString(cNotes);
	// } else {
	// return FAILED;
	// }
	// }

	// 根据主帖的id获取批量子帖
	@RequestMapping(value = "/getChildPostForId", method = RequestMethod.GET)
	public String getChildNote5(int mainPostId) {
		// ChildNote cNote = null;
		List<ChildNote> cNotes;
		cNotes = childNoteDao.findByMainPostId(mainPostId);
		if (cNotes.size() != 0) {
			// 如果序列长度为1，则直接返回主帖对象
			// if (cNotes.size() == 1) {
			// return JSON.toJSONString(cNotes.get(0));
			// }
			ChildNotes childNotes = new ChildNotes();
			childNotes.setChildPosts(cNotes);
			return JSON.toJSONString(childNotes);
		} else {
			return FAILED;
		}
	}

	@RequestMapping(value = "/newPost", method = RequestMethod.GET)
	public String newNote(String usernumber, String title, String content) {
		User user = userDao.findByNumber(usernumber);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// 发表时间
		String publishTime = format.format(date);
		String userName = user.getName();
		MainNote mainNote = new MainNote(userName, usernumber, title, 0, publishTime, content);
		mainNoteDao.save(mainNote);
		return SUCCESS;
	}

	@RequestMapping(value = "/replyPost", method = RequestMethod.GET)
	public String replyNote(String usernumber, String content, int superid) {
		User user = userDao.findByNumber(usernumber);
		String userName = user.getName();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		// 回复时间
		String replyTime = format.format(date);
		ChildNote childNote = new ChildNote(userName,usernumber,content,replyTime,superid,0);
		childNoteDao.save(childNote);
		return SUCCESS;
	}
}
