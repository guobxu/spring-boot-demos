package me.codetalk.mybatis.interceptor;

import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import me.codetalk.mybatis.annotation.Shardby;
import me.codetalk.pojo.Shardable;

@Component
@Intercepts({
	@Signature(type= Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
	@Signature(type= Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
})
public class ShardTableInterceptor implements Interceptor {

	private static final String PARAM_SHARD_RANGE = "range";
	
	public Object intercept(Invocation invocation) throws Throwable {
		Shardby annot = invocation.getMethod().getAnnotation(Shardby.class);
		if(annot != null) {
			Map<String, Object> params = (Map<String, Object>)invocation.getArgs()[1];
			Object obj = params.get(annot.value()); // 
			int range = annot.range();
			
			Long id = null;
			if(obj instanceof Long) {
				id = (Long)obj;
			} else if(obj instanceof Shardable) {
				id = ((Shardable)obj).getId();
			}
			
			if(id != null) {
				long tmp = id / range, start = tmp * range + 1, end = (tmp + 1) * range;
				params.put(PARAM_SHARD_RANGE, start + "_" + end);
			}
		}
		
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

	
}
