<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同明细</title>
	<meta name="decorator" content="default"/>
<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="gray-bg">
<div class="row">
        <div class="col-sm-9">
            <div class="wrapper wrapper-content animated fadeInUp">
                <div class="ibox">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="m-b-md">
                                    <a href="project_detail.html#" class="btn btn-white btn-xs pull-right">返回</a>
                                    <h2>阿里巴巴集团</h2>
                                </div>
                                <dl class="dl-horizontal">
                                    <dt>状态：</dt>
                                    <dd><span class="label label-primary">进行中</span>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-5">
                                <dl class="dl-horizontal">

                                    <dt>销售人员：</dt>
                                    <dd>Beaut-zihan</dd>
                                    <dt>客户：</dt>
                                    <dd>162</dd>
                                    <dt>联系方式：</dt>
                                    <dd><a href="project_detail.html#" class="text-navy"> 百度</a>
                                    </dd>
                                    <dt>联系地址：</dt>
                                    <dd>v1.4.2</dd>
                                </dl>
                            </div>
                            <div class="col-sm-7" id="cluster_info">
                                <dl class="dl-horizontal">

                                    <dt>最后更新：</dt>
                                    <dd>2014年 11月7日 22:03</dd>
                                    <dt>创建于：</dt>
                                    <dd>2014年 2月16日 03:01</dd>
                                    <dt>团队成员：</dt>
                                    <dd class="project-people">
                                        <a href="project_detail.html">
                                            <img alt="image" class="img-circle" src="img/a3.jpg">
                                        </a>
                                        <a href="project_detail.html">
                                            <img alt="image" class="img-circle" src="img/a1.jpg">
                                        </a>
                                        <a href="project_detail.html">
                                            <img alt="image" class="img-circle" src="img/a2.jpg">
                                        </a>
                                        <a href="project_detail.html">
                                            <img alt="image" class="img-circle" src="img/a4.jpg">
                                        </a>
                                        <a href="project_detail.html">
                                            <img alt="image" class="img-circle" src="img/a5.jpg">
                                        </a>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <dl class="dl-horizontal">
                                    <dt>当前安装进度</dt>
                                    <dd>
                                        <div class="progress progress-striped active m-b-sm">
                                            <div style="width: 60%;" class="progress-bar"></div>
                                        </div>
                                        <small>当前已完成安装项目总进度的 <strong>60%</strong></small>
                                    </dd>
                                </dl>
                            </div>
                        </div>
						<div class="row">
                            <div class="col-sm-12">
                                <dl class="dl-horizontal">
                                    <dt>当前回款进度</dt>
                                    <dd>
                                        <div class="progress progress-striped active m-b-sm">
                                            <div style="width: 60%;" class="progress-bar"></div>
                                        </div>
                                        <small>当前已完成项目回款总进度的 <strong>60%</strong></small>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row m-t-sm">
                            <div class="col-sm-12">
                                <div class="panel blank-panel">
                                    <div class="panel-heading">
                                        <div class="panel-options">
                                            <ul class="nav nav-tabs">
                                                <li><a href="project_detail.html#tab-1" data-toggle="tab">团队消息</a>
                                                </li>
                                                <li class=""><a href="project_detail.html#tab-2" data-toggle="tab">最后更新</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="panel-body">

                                        <div class="tab-content">
                                            <div class="tab-pane active" id="tab-1">
                                                <div class="feed-activity-list">
                                                    <div class="feed-element">
                                                        <a href="profile.html#" class="pull-left">
                                                            <img alt="image" class="img-circle" src="img/a1.jpg">
                                                        </a>
                                                        <div class="media-body ">
                                                            <small class="pull-right text-navy">1天前</small>
                                                            <strong>奔波儿灞</strong> 关注了 <strong>灞波儿奔</strong>.
                                                            <br>
                                                            <small class="text-muted">54分钟前 来自 皮皮时光机</small>
                                                            <div class="actions">
                                                                <a class="btn btn-xs btn-white"><i class="fa fa-thumbs-up"></i> 赞 </a>
                                                                <a class="btn btn-xs btn-danger"><i class="fa fa-heart"></i> 收藏</a>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="feed-element">
                                                        <a href="profile.html#" class="pull-left">
                                                            <img alt="image" class="img-circle" src="img/profile.jpg">
                                                        </a>
                                                        <div class="media-body ">
                                                            <small class="pull-right">5分钟前</small>
                                                            <strong>作家崔成浩</strong> 发布了一篇文章
                                                            <br>
                                                            <small class="text-muted">今天 10:20 来自 iPhone 6 Plus</small>

                                                        </div>
                                                    </div>

                                                    <div class="feed-element">
                                                        <a href="profile.html#" class="pull-left">
                                                            <img alt="image" class="img-circle" src="img/a2.jpg">
                                                        </a>
                                                        <div class="media-body ">
                                                            <small class="pull-right">2小时前</small>
                                                            <strong>作家崔成浩</strong> 抽奖中了20万
                                                            <br>
                                                            <small class="text-muted">今天 09:27 来自 Koryolink iPhone</small>
                                                            <div class="well">
                                                                抽奖，人民币2000元，从转发这个微博的粉丝中抽取一人。11月16日平台开奖。随手一转，万一中了呢？
                                                            </div>
                                                            <div class="pull-right">
                                                                <a class="btn btn-xs btn-white"><i class="fa fa-thumbs-up"></i> 赞 </a>
                                                                <a class="btn btn-xs btn-white"><i class="fa fa-heart"></i> 收藏</a>
                                                                <a class="btn btn-xs btn-primary"><i class="fa fa-pencil"></i> 评论</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="feed-element">
                                                        <a href="profile.html#" class="pull-left">
                                                            <img alt="image" class="img-circle" src="img/a3.jpg">
                                                        </a>
                                                        <div class="media-body ">
                                                            <small class="pull-right">2天前</small>
                                                            <strong>天猫</strong> 上传了2张图片
                                                            <br>
                                                            <small class="text-muted">11月7日 11:56 来自 微博 weibo.com</small>
                                                            <div class="photos">
                                                                <a target="_blank" href="http://24.media.tumblr.com/20a9c501846f50c1271210639987000f/tumblr_n4vje69pJm1st5lhmo1_1280.jpg">
                                                                    <img alt="image" class="feed-photo" src="img/p1.jpg">
                                                                </a>
                                                                <a target="_blank" href="http://37.media.tumblr.com/9afe602b3e624aff6681b0b51f5a062b/tumblr_n4ef69szs71st5lhmo1_1280.jpg">
                                                                    <img alt="image" class="feed-photo" src="img/p3.jpg">
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="feed-element">
                                                        <a href="profile.html#" class="pull-left">
                                                            <img alt="image" class="img-circle" src="img/a4.jpg">
                                                        </a>
                                                        <div class="media-body ">
                                                            <small class="pull-right text-navy">5小时前</small>
                                                            <strong>在水一方Y</strong> 关注了 <strong>那二十年的单身</strong>.
                                                            <br>
                                                            <small class="text-muted">今天 10:39 来自 iPhone客户端</small>
                                                            <div class="actions">
                                                                <a class="btn btn-xs btn-white"><i class="fa fa-thumbs-up"></i> 赞 </a>
                                                                <a class="btn btn-xs btn-white"><i class="fa fa-heart"></i> 收藏</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="tab-pane" id="tab-2">

                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>状态</th>
                                                            <th>标题</th>
                                                            <th>开始时间</th>
                                                            <th>结束时间</th>
                                                            <th>说明</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>
                                                                <span class="label label-primary"><i class="fa fa-check"></i> 已完成</span>
                                                            </td>
                                                            <td>
                                                                文档在线预览功能
                                                            </td>
                                                            <td>
                                                                11月7日 22:03
                                                            </td>
                                                            <td>
                                                                11月7日 20:11
                                                            </td>
                                                            <td>
                                                                <p class="small">
                                                                    已经测试通过
                                                                </p>
                                                            </td>

                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="label label-primary"><i class="fa fa-check"></i> 解决中</span>
                                                            </td>
                                                            <td>
                                                                会员登录
                                                            </td>
                                                            <td>
                                                                11月7日 22:03
                                                            </td>
                                                            <td>
                                                                11月7日 20:11
                                                            </td>
                                                            <td>
                                                                <p class="small">
                                                                    测试中
                                                                </p>
                                                            </td>

                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <span class="label label-primary"><i class="fa fa-check"></i> 解决中</span>
                                                            </td>
                                                            <td>
                                                                会员积分
                                                            </td>
                                                            <td>
                                                                11月7日 22:03
                                                            </td>
                                                            <td>
                                                                11月7日 20:11
                                                            </td>
                                                            <td>
                                                                <p class="small">
                                                                    未测试
                                                                </p>
                                                            </td>

                                                        </tr>


                                                    </tbody>
                                                </table>

                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
       
    </div>
</body>
</html>