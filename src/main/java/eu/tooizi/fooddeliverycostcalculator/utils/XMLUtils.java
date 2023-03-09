package eu.tooizi.fooddeliverycostcalculator.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class XMLUtils
{
    /**
     * Gets the first child node with the given tag name
     *
     * @param nodes the NodeList to search
     * @param tagName the tag name to search for
     * @return the first child node with the given tag name
     */
    public static Optional<Node> getChildByTagName(NodeList nodes, String tagName)
    {
        for (int i = 0; i < nodes.getLength(); i++)
        {
            if (nodes.item(i).getNodeName().equals(tagName))
            {
                return Optional.ofNullable(nodes.item(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Filters a NodeList by node names
     *
     * @param nodes the NodeList to filter
     * @param namesToFilter the names of the nodes to filter
     * @return a Collection of the filtered nodes
     */
    public static Collection<Node> filterByNodeNames(NodeList nodes, String... namesToFilter)
    {
        Collection <Node> filteredNodes = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++)
        {
            for (String name : namesToFilter)
            {
                if (nodes.item(i).getNodeName().equals(name))
                {
                    filteredNodes.add(nodes.item(i));
                }
            }
        }
        return filteredNodes;
    }
}
