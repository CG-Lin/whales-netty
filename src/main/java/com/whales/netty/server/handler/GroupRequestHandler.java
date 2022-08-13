package com.whales.netty.server.handler;

import com.whales.netty.server.entity.Group;
import com.whales.netty.server.entity.ResponseMessage;
import com.whales.netty.server.message.GroupCreateMessage;
import com.whales.netty.server.session.GroupSession;
import com.whales.netty.server.session.RecordSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class GroupRequestHandler extends SimpleChannelInboundHandler<GroupCreateMessage> {

    @Autowired
    RecordSession recordSession;

    @Autowired
    GroupSession groupSession;


    //建立连接时通报一下当前群里的连接人数
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //TODO 测试群聊的
        System.out.println("新的链接进入：" + ctx.channel().remoteAddress() + ",连接总数量" +);
    }

    //GroupCreateMessage中记录了群名和对应的群成员账户
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        //群管理器
        Group group = groupSession.createGroup(groupName, members);
        if (group == null) {
            //发送成功消息
            ctx.writeAndFlush(new ResponseMessage(true, groupName + "创建成功"));

            //发送群消息
            List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
            membersChannel.forEach(channel -> channel.writeAndFlush(new ResponseMessage(true, "您已被拉入" + groupName)));
        } else {
            ctx.writeAndFlush(new ResponseMessage(false, groupName + "已经存在"));
        }
    }
}
