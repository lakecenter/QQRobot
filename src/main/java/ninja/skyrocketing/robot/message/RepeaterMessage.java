package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.util.RandomUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RepeaterMessage {
	public static Message stupidAI(MessageEntity messageEntity) {
		if (messageEntity.getYamlFile().getIdList().get("banedgroup").contains(messageEntity.getGroupId().toString())) {
			return null;
		} else {
			int randomNum = RandomUtil.getRandomNum(100);
			if (randomNum > Integer.parseInt(messageEntity.getYamlFile().getConfigList().get("random")))
				return messageEntity.sendMsg(messageEntity.getMsg().replaceAll("吗[?？]?$", "！"));
		}
		return null;
	}
	
	public static Message morningAndNight(MessageEntity messageEntity) {
		LocalTime now = LocalTime.now();
		int hour = now.getHour();
		boolean goodNight = messageEntity.getMsg().matches("^(晚安)(鸭|啊|哦|呀|安)?|安{1,2}$");
		boolean goodMorning = messageEntity.getMsg().matches("早+(鸭|啊|哦|安|上好)*");
		boolean goodAfternoon = messageEntity.getMsg().matches("午+(安|好)*|中午好|下午好");
		Message front = messageEntity.atSomeone("\n" + TimeUtil.getClockEmoji(hour) +
				"当前时间" + now.format(DateTimeFormatter.ofPattern("HH:mm")) + "\n");
		switch (hour) {
			case 0:
			case 1:
			case 2: {
				if (goodMorning) {
					return front.plus("这么早就发早，肯定是没睡觉吧！");
				}
				if (goodAfternoon) {
					return front.plus("？你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("晚安哦，明天（今天）记得早点睡。\nおやすみなさい！");
				}
				return null;
			}
			case 3:
			case 4: {
				if (goodMorning) {
					return front.plus("gkd去睡觉，别早了，再不睡就猝死了！");
				}
				if (goodAfternoon) {
					return front.plus("？你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("别晚安了，再不睡就要发早安了。");
				}
				return null;
			}
			case 5:
			case 6: {
				if (goodMorning) {
					return front.plus("早啊，起这么早？\nおはようございます！");
				}
				if (goodAfternoon) {
					return front.plus("我没记错的话，现在还是早上吧。");
				}
				if (goodNight) {
					return front.plus("晚安？又是一晚上没睡吧。");
				}
				return null;
			}
			case 7:
			case 8:
			case 9: {
				if (goodMorning) {
					return front.plus("早啊！\nおはようございます！");
				}
				if (goodAfternoon) {
					return front.plus("我没记错的话，现在还是早上吧。");
				}
				if (goodNight) {
					return front.plus("晚安？又是一晚上没睡吧。");
				}
				return null;
			}
			case 10:
			case 11: {
				if (goodMorning) {
					return front.plus("不早了，快中午了，明天早点起来吧。");
				}
				if (goodAfternoon) {
					return front.plus("啊这，还没到中午。");
				}
				if (goodNight) {
					return front.plus("晚安？又是一晚上没睡吧。");
				}
				return null;
			}
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17: {
				if (goodMorning) {
					return front.plus("不早了，现在是下午了。");
				}
				if (goodAfternoon) {
					return front.plus("下午好啊！");
				}
				if (goodNight) {
					return front.plus("刚刚午睡起来？");
				}
				return null;
			}
			case 18:
			case 19:
			case 20: {
				if (goodMorning) {
					return front.plus("不早了，都到晚上了。");
				}
				if (goodAfternoon) {
					return front.plus("别午了，都到晚上了。");
				}
				if (goodNight) {
					return front.plus("？表面晚安？");
				}
				return null;
			}
			case 21:
			case 22:
			case 23: {
				if (goodMorning) {
					return front.plus("不早了，要睡了。");
				}
				if (goodAfternoon) {
					return front.plus("别中午了，都到晚上了。");
				}
				if (goodNight) {
					return front.plus("晚安哦。\nおやすみなさい！");
				}
				return null;
			}
		}
		return null;
	}
	
	public static Message justThis(MessageEntity messageEntity) {
		if (messageEntity.getYamlFile().getIdList().get("banedgroup").contains(messageEntity.getGroupId().toString())) {
			return null;
		} else {
			int randomNum = RandomUtil.getRandomNum(100);
			if (randomNum > Integer.parseInt(messageEntity.getYamlFile().getConfigList().get("random")))
				return messageEntity.sendMsg(messageEntity.getMsg());
		}
		return null;
	}
	
	public static Message repeaterCommand(MessageEntity messageEntity) {
		if (messageEntity.getMsg().length() > 2) {
			MessageChain messageChain = messageEntity.getGroupMessageEvent().getMessage();
			String content = messageChain.contentToString();
			int index = -1;
			if (content.contains("[图片]")) {
				for (int i = 1; i < messageChain.size(); ++i) {
					if (messageChain.get(i).contentToString().matches("\\[mirai:image:\\{.*\\}.mirai\\]")) {
						break;
					}
					index = i;
				}
				Image image = (Image) messageChain.get(index);
				return new MessageChainBuilder() {{
					add(image);
				}}.asMessageChain();
			}
			return messageEntity.sendMsg(messageEntity.getGroupMessageEvent().getMessage().contentToString().substring(2));
		} else {
			return null;
		}
	}
}