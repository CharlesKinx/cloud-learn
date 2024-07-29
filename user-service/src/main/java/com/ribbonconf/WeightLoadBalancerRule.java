package com.ribbonconf;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.List;

public class WeightLoadBalancerRule extends AbstractLoadBalancerRule {

    public Server choose(ILoadBalancer lb,Object key) {

        List<Server> upList = lb.getReachableServers();
        List<Server> allList = lb.getAllServers();

        Server server = null;

        for(Server sv : upList) {
            Integer weight = Integer.valueOf(((DiscoveryEnabledServer) sv).getInstanceInfo().getMetadata().get("weight"));
            System.out.println("权值为："+weight);
            server = sv;
        }

        return server;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        return choose(getLoadBalancer(),o);
    }
}
