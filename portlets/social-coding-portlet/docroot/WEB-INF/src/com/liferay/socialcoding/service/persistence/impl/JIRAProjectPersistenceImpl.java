/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.socialcoding.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.liferay.socialcoding.exception.NoSuchJIRAProjectException;
import com.liferay.socialcoding.model.JIRAProject;
import com.liferay.socialcoding.model.impl.JIRAProjectImpl;
import com.liferay.socialcoding.model.impl.JIRAProjectModelImpl;
import com.liferay.socialcoding.service.persistence.JIRAProjectPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the j i r a project service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JIRAProjectPersistence
 * @see com.liferay.socialcoding.service.persistence.JIRAProjectUtil
 * @generated
 */
@ProviderType
public class JIRAProjectPersistenceImpl extends BasePersistenceImpl<JIRAProject>
	implements JIRAProjectPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JIRAProjectUtil} to access the j i r a project persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JIRAProjectImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectModelImpl.FINDER_CACHE_ENABLED, JIRAProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectModelImpl.FINDER_CACHE_ENABLED, JIRAProjectImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_KEY = new FinderPath(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectModelImpl.FINDER_CACHE_ENABLED, JIRAProjectImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByKey",
			new String[] { String.class.getName() },
			JIRAProjectModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KEY = new FinderPath(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKey",
			new String[] { String.class.getName() });

	/**
	 * Returns the j i r a project where key = &#63; or throws a {@link NoSuchJIRAProjectException} if it could not be found.
	 *
	 * @param key the key
	 * @return the matching j i r a project
	 * @throws NoSuchJIRAProjectException if a matching j i r a project could not be found
	 */
	@Override
	public JIRAProject findByKey(String key) throws NoSuchJIRAProjectException {
		JIRAProject jiraProject = fetchByKey(key);

		if (jiraProject == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchJIRAProjectException(msg.toString());
		}

		return jiraProject;
	}

	/**
	 * Returns the j i r a project where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param key the key
	 * @return the matching j i r a project, or <code>null</code> if a matching j i r a project could not be found
	 */
	@Override
	public JIRAProject fetchByKey(String key) {
		return fetchByKey(key, true);
	}

	/**
	 * Returns the j i r a project where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param key the key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching j i r a project, or <code>null</code> if a matching j i r a project could not be found
	 */
	@Override
	public JIRAProject fetchByKey(String key, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { key };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_KEY,
					finderArgs, this);
		}

		if (result instanceof JIRAProject) {
			JIRAProject jiraProject = (JIRAProject)result;

			if (!Validator.equals(key, jiraProject.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_JIRAPROJECT_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
				}

				List<JIRAProject> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_KEY, finderArgs,
						list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"JIRAProjectPersistenceImpl.fetchByKey(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					JIRAProject jiraProject = list.get(0);

					result = jiraProject;

					cacheResult(jiraProject);

					if ((jiraProject.getKey() == null) ||
							!jiraProject.getKey().equals(key)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_KEY,
							finderArgs, jiraProject);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_KEY, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (JIRAProject)result;
		}
	}

	/**
	 * Removes the j i r a project where key = &#63; from the database.
	 *
	 * @param key the key
	 * @return the j i r a project that was removed
	 */
	@Override
	public JIRAProject removeByKey(String key)
		throws NoSuchJIRAProjectException {
		JIRAProject jiraProject = findByKey(key);

		return remove(jiraProject);
	}

	/**
	 * Returns the number of j i r a projects where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching j i r a projects
	 */
	@Override
	public int countByKey(String key) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KEY;

		Object[] finderArgs = new Object[] { key };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JIRAPROJECT_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_KEY_KEY_1 = "jiraProject.key IS NULL";
	private static final String _FINDER_COLUMN_KEY_KEY_2 = "jiraProject.key = ?";
	private static final String _FINDER_COLUMN_KEY_KEY_3 = "(jiraProject.key IS NULL OR jiraProject.key = '')";

	public JIRAProjectPersistenceImpl() {
		setModelClass(JIRAProject.class);
	}

	/**
	 * Caches the j i r a project in the entity cache if it is enabled.
	 *
	 * @param jiraProject the j i r a project
	 */
	@Override
	public void cacheResult(JIRAProject jiraProject) {
		entityCache.putResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectImpl.class, jiraProject.getPrimaryKey(), jiraProject);

		finderCache.putResult(FINDER_PATH_FETCH_BY_KEY,
			new Object[] { jiraProject.getKey() }, jiraProject);

		jiraProject.resetOriginalValues();
	}

	/**
	 * Caches the j i r a projects in the entity cache if it is enabled.
	 *
	 * @param jiraProjects the j i r a projects
	 */
	@Override
	public void cacheResult(List<JIRAProject> jiraProjects) {
		for (JIRAProject jiraProject : jiraProjects) {
			if (entityCache.getResult(
						JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
						JIRAProjectImpl.class, jiraProject.getPrimaryKey()) == null) {
				cacheResult(jiraProject);
			}
			else {
				jiraProject.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all j i r a projects.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JIRAProjectImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the j i r a project.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JIRAProject jiraProject) {
		entityCache.removeResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectImpl.class, jiraProject.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((JIRAProjectModelImpl)jiraProject);
	}

	@Override
	public void clearCache(List<JIRAProject> jiraProjects) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JIRAProject jiraProject : jiraProjects) {
			entityCache.removeResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
				JIRAProjectImpl.class, jiraProject.getPrimaryKey());

			clearUniqueFindersCache((JIRAProjectModelImpl)jiraProject);
		}
	}

	protected void cacheUniqueFindersCache(
		JIRAProjectModelImpl jiraProjectModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] { jiraProjectModelImpl.getKey() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_KEY, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_KEY, args,
				jiraProjectModelImpl);
		}
		else {
			if ((jiraProjectModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_KEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { jiraProjectModelImpl.getKey() };

				finderCache.putResult(FINDER_PATH_COUNT_BY_KEY, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_KEY, args,
					jiraProjectModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		JIRAProjectModelImpl jiraProjectModelImpl) {
		Object[] args = new Object[] { jiraProjectModelImpl.getKey() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_KEY, args);

		if ((jiraProjectModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_KEY.getColumnBitmask()) != 0) {
			args = new Object[] { jiraProjectModelImpl.getOriginalKey() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_KEY, args);
		}
	}

	/**
	 * Creates a new j i r a project with the primary key. Does not add the j i r a project to the database.
	 *
	 * @param jiraProjectId the primary key for the new j i r a project
	 * @return the new j i r a project
	 */
	@Override
	public JIRAProject create(long jiraProjectId) {
		JIRAProject jiraProject = new JIRAProjectImpl();

		jiraProject.setNew(true);
		jiraProject.setPrimaryKey(jiraProjectId);

		return jiraProject;
	}

	/**
	 * Removes the j i r a project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param jiraProjectId the primary key of the j i r a project
	 * @return the j i r a project that was removed
	 * @throws NoSuchJIRAProjectException if a j i r a project with the primary key could not be found
	 */
	@Override
	public JIRAProject remove(long jiraProjectId)
		throws NoSuchJIRAProjectException {
		return remove((Serializable)jiraProjectId);
	}

	/**
	 * Removes the j i r a project with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the j i r a project
	 * @return the j i r a project that was removed
	 * @throws NoSuchJIRAProjectException if a j i r a project with the primary key could not be found
	 */
	@Override
	public JIRAProject remove(Serializable primaryKey)
		throws NoSuchJIRAProjectException {
		Session session = null;

		try {
			session = openSession();

			JIRAProject jiraProject = (JIRAProject)session.get(JIRAProjectImpl.class,
					primaryKey);

			if (jiraProject == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchJIRAProjectException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(jiraProject);
		}
		catch (NoSuchJIRAProjectException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected JIRAProject removeImpl(JIRAProject jiraProject) {
		jiraProject = toUnwrappedModel(jiraProject);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(jiraProject)) {
				jiraProject = (JIRAProject)session.get(JIRAProjectImpl.class,
						jiraProject.getPrimaryKeyObj());
			}

			if (jiraProject != null) {
				session.delete(jiraProject);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (jiraProject != null) {
			clearCache(jiraProject);
		}

		return jiraProject;
	}

	@Override
	public JIRAProject updateImpl(JIRAProject jiraProject) {
		jiraProject = toUnwrappedModel(jiraProject);

		boolean isNew = jiraProject.isNew();

		JIRAProjectModelImpl jiraProjectModelImpl = (JIRAProjectModelImpl)jiraProject;

		Session session = null;

		try {
			session = openSession();

			if (jiraProject.isNew()) {
				session.save(jiraProject);

				jiraProject.setNew(false);
			}
			else {
				jiraProject = (JIRAProject)session.merge(jiraProject);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JIRAProjectModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		entityCache.putResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
			JIRAProjectImpl.class, jiraProject.getPrimaryKey(), jiraProject,
			false);

		clearUniqueFindersCache(jiraProjectModelImpl);
		cacheUniqueFindersCache(jiraProjectModelImpl, isNew);

		jiraProject.resetOriginalValues();

		return jiraProject;
	}

	protected JIRAProject toUnwrappedModel(JIRAProject jiraProject) {
		if (jiraProject instanceof JIRAProjectImpl) {
			return jiraProject;
		}

		JIRAProjectImpl jiraProjectImpl = new JIRAProjectImpl();

		jiraProjectImpl.setNew(jiraProject.isNew());
		jiraProjectImpl.setPrimaryKey(jiraProject.getPrimaryKey());

		jiraProjectImpl.setJiraProjectId(jiraProject.getJiraProjectId());
		jiraProjectImpl.setKey(jiraProject.getKey());
		jiraProjectImpl.setName(jiraProject.getName());

		return jiraProjectImpl;
	}

	/**
	 * Returns the j i r a project with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the j i r a project
	 * @return the j i r a project
	 * @throws NoSuchJIRAProjectException if a j i r a project with the primary key could not be found
	 */
	@Override
	public JIRAProject findByPrimaryKey(Serializable primaryKey)
		throws NoSuchJIRAProjectException {
		JIRAProject jiraProject = fetchByPrimaryKey(primaryKey);

		if (jiraProject == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchJIRAProjectException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return jiraProject;
	}

	/**
	 * Returns the j i r a project with the primary key or throws a {@link NoSuchJIRAProjectException} if it could not be found.
	 *
	 * @param jiraProjectId the primary key of the j i r a project
	 * @return the j i r a project
	 * @throws NoSuchJIRAProjectException if a j i r a project with the primary key could not be found
	 */
	@Override
	public JIRAProject findByPrimaryKey(long jiraProjectId)
		throws NoSuchJIRAProjectException {
		return findByPrimaryKey((Serializable)jiraProjectId);
	}

	/**
	 * Returns the j i r a project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the j i r a project
	 * @return the j i r a project, or <code>null</code> if a j i r a project with the primary key could not be found
	 */
	@Override
	public JIRAProject fetchByPrimaryKey(Serializable primaryKey) {
		JIRAProject jiraProject = (JIRAProject)entityCache.getResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
				JIRAProjectImpl.class, primaryKey);

		if (jiraProject == _nullJIRAProject) {
			return null;
		}

		if (jiraProject == null) {
			Session session = null;

			try {
				session = openSession();

				jiraProject = (JIRAProject)session.get(JIRAProjectImpl.class,
						primaryKey);

				if (jiraProject != null) {
					cacheResult(jiraProject);
				}
				else {
					entityCache.putResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
						JIRAProjectImpl.class, primaryKey, _nullJIRAProject);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
					JIRAProjectImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return jiraProject;
	}

	/**
	 * Returns the j i r a project with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param jiraProjectId the primary key of the j i r a project
	 * @return the j i r a project, or <code>null</code> if a j i r a project with the primary key could not be found
	 */
	@Override
	public JIRAProject fetchByPrimaryKey(long jiraProjectId) {
		return fetchByPrimaryKey((Serializable)jiraProjectId);
	}

	@Override
	public Map<Serializable, JIRAProject> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JIRAProject> map = new HashMap<Serializable, JIRAProject>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JIRAProject jiraProject = fetchByPrimaryKey(primaryKey);

			if (jiraProject != null) {
				map.put(primaryKey, jiraProject);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			JIRAProject jiraProject = (JIRAProject)entityCache.getResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
					JIRAProjectImpl.class, primaryKey);

			if (jiraProject == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, jiraProject);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_JIRAPROJECT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (JIRAProject jiraProject : (List<JIRAProject>)q.list()) {
				map.put(jiraProject.getPrimaryKeyObj(), jiraProject);

				cacheResult(jiraProject);

				uncachedPrimaryKeys.remove(jiraProject.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(JIRAProjectModelImpl.ENTITY_CACHE_ENABLED,
					JIRAProjectImpl.class, primaryKey, _nullJIRAProject);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the j i r a projects.
	 *
	 * @return the j i r a projects
	 */
	@Override
	public List<JIRAProject> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the j i r a projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JIRAProjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of j i r a projects
	 * @param end the upper bound of the range of j i r a projects (not inclusive)
	 * @return the range of j i r a projects
	 */
	@Override
	public List<JIRAProject> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the j i r a projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JIRAProjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of j i r a projects
	 * @param end the upper bound of the range of j i r a projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of j i r a projects
	 */
	@Override
	public List<JIRAProject> findAll(int start, int end,
		OrderByComparator<JIRAProject> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the j i r a projects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JIRAProjectModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of j i r a projects
	 * @param end the upper bound of the range of j i r a projects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of j i r a projects
	 */
	@Override
	public List<JIRAProject> findAll(int start, int end,
		OrderByComparator<JIRAProject> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<JIRAProject> list = null;

		if (retrieveFromCache) {
			list = (List<JIRAProject>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_JIRAPROJECT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JIRAPROJECT;

				if (pagination) {
					sql = sql.concat(JIRAProjectModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<JIRAProject>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JIRAProject>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the j i r a projects from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JIRAProject jiraProject : findAll()) {
			remove(jiraProject);
		}
	}

	/**
	 * Returns the number of j i r a projects.
	 *
	 * @return the number of j i r a projects
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JIRAPROJECT);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return JIRAProjectModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the j i r a project persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(JIRAProjectImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_JIRAPROJECT = "SELECT jiraProject FROM JIRAProject jiraProject";
	private static final String _SQL_SELECT_JIRAPROJECT_WHERE_PKS_IN = "SELECT jiraProject FROM JIRAProject jiraProject WHERE id IN (";
	private static final String _SQL_SELECT_JIRAPROJECT_WHERE = "SELECT jiraProject FROM JIRAProject jiraProject WHERE ";
	private static final String _SQL_COUNT_JIRAPROJECT = "SELECT COUNT(jiraProject) FROM JIRAProject jiraProject";
	private static final String _SQL_COUNT_JIRAPROJECT_WHERE = "SELECT COUNT(jiraProject) FROM JIRAProject jiraProject WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "jiraProject.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JIRAProject exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JIRAProject exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(JIRAProjectPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"jiraProjectId", "key", "name"
			});
	private static final JIRAProject _nullJIRAProject = new JIRAProjectImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JIRAProject> toCacheModel() {
				return _nullJIRAProjectCacheModel;
			}
		};

	private static final CacheModel<JIRAProject> _nullJIRAProjectCacheModel = new CacheModel<JIRAProject>() {
			@Override
			public JIRAProject toEntityModel() {
				return _nullJIRAProject;
			}
		};
}