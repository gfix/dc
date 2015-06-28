package no.gfix.crawler.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.StatementTree;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModuleWrapper {
    private final Map<Name, BlockTree> methodMap;
    private final String moduleId;

    public ModuleWrapper(Map<Name, BlockTree> methodMap, String moduleId) {
        this.methodMap = methodMap;
        this.moduleId = moduleId;
    }

    public static ModuleWrapper create(String moduleId, Map<Name, BlockTree> methodMap) {
        return new ModuleWrapper(methodMap, moduleId);
    }

    public String getModuleId() {
        return moduleId;
    }

    public List<String> getStatements() {
        List<String> statements = new ArrayList<String>(methodMap.keySet().size() * methodMap.values().size());
        for (BlockTree blockTree : methodMap.values()) {
            statements.addAll(Lists.transform(blockTree.getStatements(), new Function<StatementTree, String>() {
                @Override
                public String apply(StatementTree input) {
                    return input.toString();
                }
            }));
        }
        return statements;
    }
}
