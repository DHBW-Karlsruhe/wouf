/*
 * Copyright (c) 2003-2009 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jgoodies.validation;

import java.io.Serializable;
import java.util.*;

import com.jgoodies.validation.message.SimpleValidationMessage;

/**
 * Describes a validation result as a list of ValidationMessages.
 * You can add single validation messages, single text messages,
 * lists of messages, and all messages from another ValidationResult.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.20 $
 *
 * @see     ValidationMessage
 * @see     Validator
 */
public final class ValidationResult implements Serializable {

    /**
     * A constant for an empty and unmodifiable validation result.
     */
    public static final ValidationResult EMPTY =
        new ValidationResult(Collections.<ValidationMessage>emptyList(), false);

    /**
     * Holds a List of ValidationMessages.
     */
    private final List<ValidationMessage> messageList;

    /**
     * Describes if this result can be modified or not.
     */
    private final boolean modifiable;


    // Instance Creation ******************************************************

    /**
     * Constructs an empty modifiable ValidationResult.
     */
    public ValidationResult() {
        this(new ArrayList<ValidationMessage>(), true);
    }


    /**
     * Constructs a ValidationResult on the given message list.
     * Used for constructing the <code>EMPTY</code> validation result.
     *
     * @param messageList   an initial message list
     * @param modifiable    true to allow modifications, false to prevent them
     */
    private ValidationResult(List<ValidationMessage> messageList, boolean modifiable) {
        this.messageList = messageList;
        this.modifiable  = modifiable;
    }


    /**
     * Returns an unmodifiable view of the given ValidationResult.
     * Useful to provide users with "read-only" access to internal results,
     * or to indicate to other validation result processors that a result
     * is not intended to be modified. Attempts to modify the returned
     * validation result throw an <code>UnsupportedOperationException</code>.
     *
     * @param validationResult  the result for which an unmodifiable view is to be returned
     * @return an unmodifiable view of the specified validation result
     */
    public static ValidationResult unmodifiableResult(ValidationResult validationResult) {
        return validationResult.modifiable
            ? new ValidationResult(new ArrayList<ValidationMessage>(validationResult.messageList),
                                   false)
            : validationResult;
    }


    // Adding Messages ********************************************************

    /**
     * Adds a new ValidationMessage to the list of messages.
     *
     * @param validationMessage   the message to add
     *
     * @throws NullPointerException           if the message is {@code null}
     * @throws UnsupportedOperationException  if the result is unmodifiable
     * @throws IllegalArgumentException       if the severity is <code>OK</code>
     *
     * @see #addError(String)
     * @see #addWarning(String)
     */
    public void add(ValidationMessage validationMessage) {
        assertModifiable();
        if (validationMessage == null)
            throw new NullPointerException("The validation message must not be null.");
        if (validationMessage.severity() == Severity.OK)
            throw new IllegalArgumentException("You must not add a validation message with severity OK.");

        messageList.add(validationMessage);
    }


    /**
     * Creates and adds an error message to the list of validation messages
     * using the given text.
     *
     * @param text   the error text to add
     *
     * @throws NullPointerException if the message text {@code null}
     * @throws UnsupportedOperationException  if the result is unmodifiable
     *
     * @see #add(ValidationMessage)
     * @see #addWarning(String)
     */
    public void addError(String text) {
        assertModifiable();
        if (text == null)
            throw new NullPointerException("The message text must not be null.");

        add(new SimpleValidationMessage(text, Severity.ERROR));
    }


    /**
     * Creates and adds a warning message to the list of validation messages
     * using the given text.
     *
     * @param text   the warning text to add
     *
     * @throws NullPointerException if the message text {@code null}
     * @throws UnsupportedOperationException  if the result is unmodifiable
     *
     * @see #add(ValidationMessage)
     * @see #addError(String)
     */
    public void addWarning(String text) {
        assertModifiable();
        if (text == null)
            throw new NullPointerException("The message text must not be null.");

        add(new SimpleValidationMessage(text));
    }


    /**
     * Adds all messages from the given list to this validation result.
     *
     * @param messages  the messages to be added
     *
     * @throws NullPointerException           if the messages list is {@code null}
     * @throws UnsupportedOperationException  if the result is unmodifiable
     * @throws IllegalArgumentException       if the messages list contains
     *     a message with severity <code>OK</code>
     *
     * @see #addAllFrom(ValidationResult)
     */
    public void addAll(List<ValidationMessage> messages) {
        assertModifiable();
        if (messages == null)
            throw new NullPointerException("The messages list must not be null.");
        for (ValidationMessage message : messages) {
            if (message.severity() == Severity.OK) {
                throw new IllegalArgumentException("You must not add a validation message with severity OK.");
            }
        }
        messageList.addAll(messages);
    }


    /**
     * Adds all messages from the given ValidationResult
     * to the list of messages that this validation result holds.
     *
     * @param validationResult  the validation result to add messages from
     *
     * @throws NullPointerException if the validation result is {@code null}
     * @throws UnsupportedOperationException  if the result is unmodifiable
     *
     * @see #addAll(List)
     */
    public void addAllFrom(ValidationResult validationResult) {
        assertModifiable();
        if (validationResult == null)
            throw new NullPointerException("The validation result to add must not be null.");

        addAll(validationResult.messageList);
    }


    // List Operations ********************************************************

    /**
     * Checks and answers whether this validation result contains no messages.
     *
     * @return true if this validation result contains no messages
     *
     * @see #hasErrors()
     * @see #hasWarnings()
     */
    public boolean isEmpty() {
        return messageList.isEmpty();
    }


    /**
     * Returns the number of messages in this result.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return messageList.size();
    }


    /**
     * Checks and answers whether this result contains the specified message.
     * More formally, returns {@code true} if and only if this result
     * contains at least one message <code>m</code> such that
     * <code>(message.equals(m))</code>.
     *
     * @param message message whose presence in this result is to be tested
     * @return {@code true} if this result contains the specified message
     * @throws NullPointerException if the specified message is
     *         {@code null}
     */
    public boolean contains(ValidationMessage message) {
        return messageList.contains(message);
    }


    /**
     * Returns an unmodifiable view of the portion of this result between
     * the specified <code>fromIndex</code>, inclusive, and <code>toIndex</code>,
     * exclusive.
     * (If <code>fromIndex</code> and <code>toIndex</code> are equal,
     * the returned result is empty.)  The returned result is a copy,
     * so changes in the returned result won't affect this result,
     * and vice-versa.
     *
     * @param fromIndex  low end point (inclusive) of the subResult
     * @param toIndex    high end point (exclusive) of the subResult
     * @return a view of the specified range within this result.
     *
     * @throws IndexOutOfBoundsException for an illegal end point index value
     *     (fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt; toIndex).
     *
     * @see #subResult(Object)
     */
    public ValidationResult subResult(int fromIndex, int toIndex) {
        List<ValidationMessage> messages = messageList.subList(fromIndex, toIndex);
        return new ValidationResult(messages, false);
    }


    /**
     * Returns an unmodifiable sub result of this result that consists of
     * all messages that share the specified message key. If the specified key
     * is {@code null}, this method returns an empty result.
     * The returned result is a copy, so changes in this result won't affect it.
     *
     * @param messageKey    the key to look for, can be {@code null}
     * @return a sub result containing all messages that share the
     *     specified key, or the empty result if the key is {@code null}
     *
     * @see #subResult(int, int)
     */
    public ValidationResult subResult(Object messageKey) {
        if (messageKey == null)
            return EMPTY;

        List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        for (ValidationMessage message : messageList) {
            if (messageKey.equals(message.key())) {
                messages.add(message);
            }
        }
        return new ValidationResult(messages, false);
    }


    /**
     * Returns an unmodifiable sub result of this result that consists of
     * all messages that share the specified message keys. If the array of keys
     * is {@code null}, this method returns an empty result.
     * The returned result is a copy, so changes in this result won't affect it.
     *
     * @param messageKeys    the keys to look for, can be {@code null}
     * @return a sub result containing all messages that share the specified
     * keys, or the empty result if the key array is {@code null}
     *
     * @see #subResult(int, int)
     *
     * @since 1.4
     */
    public ValidationResult subResult(Object[] messageKeys) {
        if (messageKeys == null) {
            return EMPTY;
        }
        List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        for (ValidationMessage message : messageList) {
            Object messageKey = message.key();
            for (Object key : messageKeys) {
                if (messageKey.equals(key)) {
                    messages.add(message);
                }
            }
        }
        return new ValidationResult(messages, false);
    }


    /**
     * Creates and returns an unmodifiable Map that maps the message keys
     * of this validation result to unmodifiable sub results that share the key.<p>
     *
     * More formally:
     * for each key <code>key</code> in the created map <code>map</code>,
     * <code>map.get(key)</code> returns a <code>ValidationResult</code>
     * <code>result</code>, such that for each <code>ValidationMessage</code>
     * <code>message</code> in <code>result</code> we have:
     * <code>message.key().equals(key)</code>.
     *
     * @return a mapping from message key to an associated validation result
     *     that consist only of messages with this key
     *
     * @see ValidationMessage#key()
     */
    public Map<Object, ValidationResult> keyMap() {
        Map<Object, List<ValidationMessage>> messageMap =
            new HashMap<Object, List<ValidationMessage>>();
        for (ValidationMessage message : messageList) {
            Object key = message.key();
            List<ValidationMessage> associatedMessages = messageMap.get(key);
            if (associatedMessages == null) {
                associatedMessages = new LinkedList<ValidationMessage>();
                messageMap.put(key, associatedMessages);
            }
            associatedMessages.add(message);
        }
        Map<Object, ValidationResult> resultMap =
            new HashMap<Object, ValidationResult>(messageMap.size());
        for (Map.Entry<Object, List<ValidationMessage>> entry : messageMap.entrySet()) {
            Object key = entry.getKey();
            List<ValidationMessage> messages = entry.getValue();
            resultMap.put(key, new ValidationResult(messages, false));
        }
        return Collections.unmodifiableMap(resultMap);
    }


    // Requesting Information *************************************************

    /**
     * Returns the highest severity of this result's messages,
     * <code>Severity.OK</code> if there are no messages.
     *
     * @return the highest severity of this result's messages,
     *     <code>Severity.OK</code> if there are no messages
     *
     * @see #hasMessages()
     * @see #hasErrors()
     * @see #hasWarnings()
     */
    public Severity getSeverity() {
        return getSeverity(messageList);
    }


    /**
     * Checks and answers whether this validation result has messages or not.
     *
     * @return true if there are messages, false if not
     *
     * @see #getSeverity()
     * @see #hasErrors()
     * @see #hasWarnings()
     */
    public boolean hasMessages() {
        return !isEmpty();
    }


    /**
     * Checks and answers whether this validation result
     * contains a message of type <code>ERROR</code>.
     *
     * @return true if there are error messages, false if not
     *
     * @see #getSeverity()
     * @see #hasMessages()
     * @see #hasWarnings()
     */
    public boolean hasErrors() {
        return hasSeverity(messageList, Severity.ERROR);
    }


    /**
     * Checks and answers whether this validation result
     * contains a message of type <code>WARNING</code>.<p>
     *
     * Note that this method checks for warning messages only.
     * It'll return false, if there are errors but no warnings.
     * If you want to test whether this result contains
     * warning and/or errors, use <code>#hasMessages</code> instead.
     *
     * @return true if there are warnings, false if not
     *
     * @see #getSeverity()
     * @see #hasMessages()
     * @see #hasErrors()
     */
    public boolean hasWarnings() {
        return hasSeverity(messageList, Severity.WARNING);
    }


    /**
     * Returns an unmodifiable List of all validation messages.
     *
     * @return the <code>List</code> of all validation messages
     *
     * @see #getErrors()
     * @see #getWarnings()
     */
    public List<ValidationMessage> getMessages() {
        return Collections.unmodifiableList(messageList);
    }


    /**
     * Returns an unmodifiable List of the validation messages
     * that indicate errors.
     *
     * @return the List of error validation messages
     *
     * @see #getMessages()
     * @see #getWarnings()
     */
    public List<ValidationMessage> getErrors() {
        return getMessagesWithSeverity(messageList, Severity.ERROR);
    }


    /**
     * Returns an unmodifiable List of the validation messages
     * that indicate warnings.
     *
     * @return the List of validation warnings
     *
     * @see #getMessages()
     * @see #getErrors()
     */
    public List<ValidationMessage> getWarnings() {
        return getMessagesWithSeverity(messageList, Severity.WARNING);
    }


    // Requesting State *******************************************************

    /**
     * Returns if this validation result is modifiable or not.
     * Can be used to cache data from unmodifiable result.
     *
     * @return true if modifiable, false if unmodifiable
     */
    public boolean isModifiable() {
        return modifiable;
    }


    // String Conversion ******************************************************

    /**
     * Returns a string representation of the message list.
     *
     * @return a string representation of the message list
     */
    public String getMessagesText() {
        return getMessagesText(messageList);
    }


    /**
     * Returns a string representation intended for debugging purposes.
     *
     * @return a string representation intended for debugging
     * @see Object#toString()
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Empty ValidationResult";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(modifiable ? "Modifiable" : "Unmodifiable");
        builder.append(" ValidationResult:");
        for (ValidationMessage message : messageList) {
            builder.append("\n\t").append(message);
        }
        return builder.toString();
    }


    // Comparison and Hashing *************************************************

    /**
     * Compares the specified object with this validation result for equality.
     * Returns {@code true} if and only if the specified object is also
     * a validation result, both results have the same size, and all
     * corresponding pairs of validation messages in the two validation results
     * are <i>equal</i>. (Two validation messages <code>m1</code> and
     * <code>m2</code> are <i>equal</i> if <code>(m1==null ? m2==null :
     * m1.equals(m2))</code>.) In other words, two validation results
     * are defined to be equal if and only if they contain the same
     * validation messages in the same order.<p>
     *
     * This implementation first checks if the specified object is this
     * validation result. If so, it returns {@code true};
     * if not, it checks if the specified object is a validation result.
     * If not, it returns {@code false}; if so, it checks and returns
     * if the lists of messages in both results are equal.
     *
     * @param o the object to be compared for equality with this validation result.
     *
     * @return {@code true} if the specified object is equal
     *     to this validation result.
     *
     * @see List#equals(java.lang.Object)
     * @see Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ValidationResult))
            return false;
        return messageList.equals(((ValidationResult) o).messageList);
    }


    /**
     * Returns the hash code value for this validation result. This
     * implementation returns the hash from the List of messages.
     *
     * @return the hash code value for this validation result.
     *
     * @see List#hashCode()
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return messageList.hashCode();
    }


    // Helper Code ************************************************************

    /**
     * Checks if this validation result is modifiable.
     * Throws an UnsupportedOperationException otherwise.
     */
    private void assertModifiable() {
        if (!modifiable)
            throw new UnsupportedOperationException(
                    "This validation result is unmodifiable.");
    }


    /**
     * Returns the highest severity of this result's messages,
     * <code>Severity.OK</code> if there are no messages.
     * A single validation message can have only the severity
     * error or warning. Hence, this method returns the error severity
     * if there's at least one error message; and it returns
     * the warning severity, otherwise - assuming that there are
     * no other severities.<p>
     *
     * TODO: Consider changing the iteration to build the
     * maximum of the message severities. This would make it
     * easier for users that want to change this library's semantics.
     * For example if someone adds Severity.FATAL, this algorithm
     * may return an unexpected result.
     *
     * @param messages  the List of ValidationMessages to check
     * @return the highest severity of this result's messages,
     *     <code>Severity.OK</code> if there are no messages
     */
    private static Severity getSeverity(List<ValidationMessage> messages) {
        if (messages.isEmpty()) {
            return Severity.OK;
        }
        for (ValidationMessage message : messages) {
            if (message.severity() == Severity.ERROR) {
                return Severity.ERROR;
            }
        }
        return Severity.WARNING;
    }

    /**
     * Checks and answers whether the given list of validation messages
     * includes message with the specified Severity.
     *
     * @param messages  the List of ValidationMessages to check
     * @param severity  the Severity to check
     * @return true if the given messages list includes error messages,
     *     false if not
     */
    private static boolean hasSeverity(
            List<ValidationMessage> messages, Severity severity) {
        for (ValidationMessage message : messages) {
            if (message.severity() == severity) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns an unmodifiable List of ValidationMessage that
     * that is the sublist of message with the given Severity.
     *
     * @param messages  the List of ValidationMessages to iterate
     * @param severity  the Severity to look for
     * @return the sublist of error messages
     */
    private static List<ValidationMessage> getMessagesWithSeverity(
            List<ValidationMessage> messages, Severity severity) {
        List<ValidationMessage> errorMessages = new ArrayList<ValidationMessage>();
        for (ValidationMessage message : messages) {
            if (message.severity() == severity) {
                errorMessages.add(message);
            }
        }
        return Collections.unmodifiableList(errorMessages);
    }


    /**
     * Returns a string representation of the given list of messages.
     *
     * @param messages  the List of ValidationMessages to iterate
     * @return a string representation of the given list of messages
     */
    private static String getMessagesText(List<ValidationMessage> messages) {
        if (messages.isEmpty()) {
            return "OK";
        }
        StringBuilder builder = new StringBuilder();
        for (ValidationMessage message : messages) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(message.formattedText());
        }
        return builder.toString();
    }


}
